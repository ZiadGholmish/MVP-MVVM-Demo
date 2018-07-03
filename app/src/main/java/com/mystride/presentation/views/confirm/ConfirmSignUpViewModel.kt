package com.mystride.presentation.views.confirm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler
import com.mystride.data.remote.models.CreateUserResult
import com.mystride.data.remote.models.RequestState
import com.mystride.data.remote.models.ResendSMSResult
import com.mystride.data.repository.Repository
import com.mystride.presentation.utils.AppHelper
import java.lang.Exception
import javax.inject.Inject

class ConfirmSignUpViewModel @Inject constructor(val repository: Repository) : ViewModel(), VerificationHandler {

    val resendSMSResultLiveData = MutableLiveData<ResendSMSResult>()
    val requestState = MutableLiveData<RequestState>()

    fun resendSMSCode() {
        requestState.value = RequestState.Loading
        if (AppHelper.user == null) {
            IllegalStateException("User can not be null")
            return
        }
        repository.resendSMSCode(AppHelper.user!!, this)
    }

    override fun onSuccess(verificationCodeDeliveryMedium: CognitoUserCodeDeliveryDetails?) {
        requestState.value = RequestState.Complete
        resendSMSResultLiveData.value = ResendSMSResult.Success
    }

    override fun onFailure(exception: Exception) {
        requestState.value = RequestState.Complete
        resendSMSResultLiveData.value = ResendSMSResult.AWSError(AppHelper.formatException(exception))
        exception.printStackTrace()
    }
}