package com.mystride.data.repository

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
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

class Repository @Inject constructor(val context: Context, val gson: Gson, val userPool: CognitoUserPool) {

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

    fun registerUser(userId: String, password: String,
                     userAttributes: CognitoUserAttributes, signUpHandler: SignUpHandler) {
        userPool.signUpInBackground(userId, password, userAttributes, null, signUpHandler)
    }

    fun resendSMSCode(userId: String, verificationHandler: VerificationHandler) {
        userPool.getUser(userId).resendConfirmationCodeInBackground(verificationHandler)
    }


}