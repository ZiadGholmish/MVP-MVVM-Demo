package com.mystride.presentation.views.phone

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.mystride.constatns.AppConstants
import com.mystride.constatns.UserPoolConstants
import com.mystride.data.repository.Repository
import com.mystride.presentation.utils.AppHelper
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class SignupPhoneViewModel @Inject constructor(val repository: Repository) : ViewModel(), SignUpHandler {

    fun createUser(firstName: String, lastName: String, phone: String, countryCode: String) {
        val userAttributes = createUserAttributes(firstName, lastName, phone, countryCode)
        repository.registerUser(AppHelper.generateUserId(), AppHelper.generateRandomPassword(), userAttributes, this)
    }

    /**
     * format the form data and create user attributes
     */
    private fun createUserAttributes(firstName: String, lastName: String, phone: String, countryCode: String): CognitoUserAttributes {
        val userAttributes = CognitoUserAttributes()
        val phoneWithCode = "$countryCode$phone"
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_EMAIL, "")
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_PHONE_NUMBER, phoneWithCode)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_GIVEN_NAME, firstName)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_FAMILY_NAME, lastName)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_LOCALE, UserPoolConstants.LOCALE_US)
        return userAttributes
    }

    override fun onSuccess(user: CognitoUser?, signUpConfirmationState: Boolean,
                           cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?) {
        if (signUpConfirmationState) {
            Log.e("signUpConfirmationState", "user already confirmed")
        } else {
            Log.e("signUpConfirmationState", "user need to be confirmed")
        }
    }

    override fun onFailure(exception: Exception?) {
        exception?.printStackTrace()
    }
}