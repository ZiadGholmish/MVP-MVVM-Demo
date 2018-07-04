package com.mystride.presentation.utils

import android.util.Log
import android.widget.EditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.mystride.constatns.AppConstants
import com.mystride.constatns.UserPoolConstants
import com.mystride.constatns.UserPoolConstants.GENERAL_EXCEPTION
import java.util.*

object AppHelper {
    private const val TAG = "AppHelper"

    var user: String? = null

    /**
     * generate user id using the uuid mechanism and make sure it is match the criteria
     */
    fun generateUserId(): String {
        val userId = "${UserPoolConstants.USER_ID_PREFIX}${UUID.randomUUID()}"
        if (!userId.matches(Regex(UserPoolConstants.USER_ID_REGEX))) {
            IllegalStateException("user id is not valid")
        }
        user = userId
        return userId
    }

    /**
     * generate a random secure password to confirm the criteria
     */
    fun generateRandomPassword(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@#=+!£\$%&?"
        var passWord = StringBuilder()
        for (i in 0..31) {
            passWord.append(chars[Math.floor(Math.random() * chars.length).toInt()])
        }
        return passWord.toString()
    }

    /**
     * get the exception message as string
     */
    fun formatException(exception: Exception): Pair<String, String> {
        var formattedString = "Internal Error"
        Log.e(TAG, " -- Error: " + exception.toString())
        Log.getStackTraceString(exception)
        val temp = exception.message
        if (temp != null && !temp.isEmpty()) {
            formattedString = temp.split("\\(")[0]
        }
        return Pair(formattedString, getErrorCode(exception))
    }


    /**
     * check the error code that come from the exception
     */
    private fun getErrorCode(exception: Exception): String {
        val temp = exception.message
        if (temp != null && !temp.isEmpty()) {
            if (temp.contains(UserPoolConstants.ALIAS_EXISTS_EXCEPTION)) {
                return UserPoolConstants.ALIAS_EXISTS_EXCEPTION
            }

            if (temp.contains(UserPoolConstants.LIMIT_EXCEEDED_EXCEPTION)) {
                return UserPoolConstants.LIMIT_EXCEEDED_EXCEPTION
            }

            if (temp.contains(UserPoolConstants.CODE_MISMATCH_EXCEPTION)) {
                return UserPoolConstants.CODE_MISMATCH_EXCEPTION
            }
        }
        return UserPoolConstants.GENERAL_EXCEPTION
    }
}

