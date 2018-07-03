package com.mystride.presentation.utils

import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.mystride.constatns.UserPoolConstants
import java.util.*


object AppHelper {

    fun generateUserId(): String {
        val userId = "${UserPoolConstants.USER_ID_PREFIX}${UUID.randomUUID()}"
        if (!userId.matches(Regex(UserPoolConstants.USER_ID_REGEX))) {
            IllegalStateException("user id is not valid")
        }
        return userId
    }

    fun generateRandomPassword(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@#=+!£\$%&?"
        var passWord = StringBuilder()
        for (i in 0..31) {
            passWord.append(chars[Math.floor(Math.random() * chars.length).toInt()])
        }
        return passWord.toString()
    }
}
