package com.mystride.presentation.views.phone

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.mystride.constatns.AppConstants
import com.mystride.constatns.UserPoolConstants
import com.mystride.data.repository.Repository
import java.util.*
import javax.inject.Inject

class SignupPhoneViewModel @Inject constructor( val repository: Repository) : ViewModel() {

    fun createUser(firstName: String, lastName: String, phone: String, countryCode: String) {
    }

    /**
     * format the form data and create user attributes
     */
    private fun createUserAttributes(firstName: String, lastName: String, phone: String, countryCode: String) {
        val userAttributes = CognitoUserAttributes()
        val phoneWithCode = "$countryCode$phone"
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_EMAIL, "")
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_PHONE_NUMBER, phoneWithCode)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_GIVEN_NAME, firstName)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_FAMILY_NAME, lastName)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_LOCALE, UserPoolConstants.LOCALE_US)
    }


    /**
     * generate the user id using uuid and the predefined prefix
     */
    private fun generateUserId(): String {
        val userId = "${UserPoolConstants.USER_ID_PREFIX}${UUID.randomUUID()}"
        Log.e("userid", userId)
        if (!userId.matches(Regex(UserPoolConstants.USER_ID_REGEX))) {
            IllegalStateException("user id is not valid")
        }
        return userId
    }

    fun generateRandomPassword(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var passWord = ""
        for (i in 0..31) {
            passWord += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        Log.e("passowd", passWord)
        return passWord
    }


    fun CognitoUserAttributes.generateUserId(): String{
        val userId = "${UserPoolConstants.USER_ID_PREFIX}${UUID.randomUUID()}"
        Log.e("userid", userId)
        if (!userId.matches(Regex(UserPoolConstants.USER_ID_REGEX))) {
            IllegalStateException("user id is not valid")
        }
        return userId
    }


    fun CognitoUserAttributes.generateRandomPassword(): String{
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var passWord = ""
        for (i in 0..31) {
            passWord += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        Log.e("passowd", passWord)
        return passWord
    }
}