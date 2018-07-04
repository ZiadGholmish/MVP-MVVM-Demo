package com.mystride.data.repository

import android.content.Context
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mystride.data.remote.models.CountryModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import javax.inject.Inject
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import java.lang.Exception


class Repository @Inject constructor(val context: Context, val gson: Gson, val userPool: CognitoUserPool, val cognitoCachingCredentialsProvider: CognitoCachingCredentialsProvider) {

    /**
     * load the countries list from the local json file
     * @return observable has the list of the available countries in the local file
     */
    fun getCountriesList(): Single<List<CountryModel>> {
        return Single.create<List<CountryModel>> {
            val countriesToken = object : TypeToken<List<CountryModel>>() {}
            var json: String?
            try {
                val inputStream = context.assets.open("countries.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charset.forName("UTF-8"))
                it.onSuccess(gson.fromJson(json, countriesToken.type))
            } catch (ex: IOException) {
                ex.printStackTrace()
                it.onError(ex)
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * fire tha action to register the user to the user pool
     */
    fun registerUser(userId: String, password: String,
                     userAttributes: CognitoUserAttributes, signUpHandler: SignUpHandler) {
        userPool.signUpInBackground(userId, password, userAttributes, null, signUpHandler)
    }

    /**
     * resend the sms code to the phone number
     */
    fun resendSMSCode(userId: String, verificationHandler: VerificationHandler) {
        userPool.getUser(userId).resendConfirmationCodeInBackground(verificationHandler)
    }

    /**
     * confirm the sign up and fail if there is a user with the same phone in the same user pool
     */
    fun confirmSignUp(userId: String, smsCode: String, genericHandler: GenericHandler) {
        val forcedAliasCreation = true
        userPool.getUser(userId).confirmSignUpInBackground(smsCode, forcedAliasCreation, genericHandler)
    }

    fun setHandleForTheUser(userId: String, password: String, cognitoUserAttributes: CognitoUserAttributes, updateAttributesHandler: UpdateAttributesHandler) {

        Observable.create<Unit> {
            val authenticationDetails = AuthenticationDetails(userId, password, mapOf())
            userPool.getUser(userId).initiateUserAuthentication(authenticationDetails, object : AuthenticationHandler {
                override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                    userSession
                    cognitoCachingCredentialsProvider.logins
                    userPool.getUser(userId).updateAttributesInBackground(cognitoUserAttributes,
                            updateAttributesHandler)

                }

                override fun onFailure(exception: Exception?) {
                    exception?.printStackTrace()
                }

                override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation?, userId: String?) {

                    authenticationContinuation
                }

                override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                    continuation

                }

                override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {

                    continuation
                }
            }, true).run()

        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { }
    }
}