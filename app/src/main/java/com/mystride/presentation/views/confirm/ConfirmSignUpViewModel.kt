package com.mystride.presentation.views.confirm

import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler
import com.mystride.presentation.utils.AppHelper
import java.lang.Exception
import javax.inject.Inject

class ConfirmSignUpViewModel @Inject constructor(val userPool: CognitoUserPool) : ViewModel() , VerificationHandler {

      fun resendSMSCode() {
        userPool.getUser(AppHelper.user).resendConfirmationCodeInBackground(this)
    }

    override fun onSuccess(verificationCodeDeliveryMedium: CognitoUserCodeDeliveryDetails?) {

        verificationCodeDeliveryMedium
    }

    override fun onFailure(exception: Exception?) {
        exception?.printStackTrace()
    }
}