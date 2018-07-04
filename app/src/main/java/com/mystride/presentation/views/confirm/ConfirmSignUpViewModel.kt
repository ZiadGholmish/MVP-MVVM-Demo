package com.mystride.presentation.views.confirm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler
import com.mystride.data.remote.models.ConfirmCodeResult
import com.mystride.data.remote.models.CreateUserResult
import com.mystride.data.remote.models.RequestState
import com.mystride.data.remote.models.ResendSMSResult
import com.mystride.data.repository.Repository
import com.mystride.presentation.utils.AppHelper
import java.lang.Exception
import javax.inject.Inject

class ConfirmSignUpViewModel @Inject constructor(val repository: Repository) : ViewModel(), VerificationHandler, GenericHandler {

    val resendSMSResultLiveData = MutableLiveData<ResendSMSResult>()
    val confirmCodeResultLiveData = MutableLiveData<ConfirmCodeResult>()
    val requestState = MutableLiveData<RequestState>()

    fun resendSMSCode() {
        requestState.value = RequestState.Loading
        if (AppHelper.user == null) {
            IllegalStateException("User can not be null")
            return
        }
        repository.resendSMSCode(AppHelper.user!!, this)
    }

    fun confirmSMSCode(smsCode: String) {
        requestState.value = RequestState.Loading
        if (AppHelper.user == null) {
            IllegalStateException("User can not be null")
            return
        }
        repository.confirmSignUp(AppHelper.user!!, smsCode, this)
    }

    override fun onSuccess(verificationCodeDeliveryMedium: CognitoUserCodeDeliveryDetails?) {
        requestState.value = RequestState.Complete
        confirmCodeResultLiveData.value = ConfirmCodeResult.Success
    }

    override fun onFailure(exception: Exception) {
        requestState.value = RequestState.Complete
        val errorPair = AppHelper.formatException(exception)
        resendSMSResultLiveData.value = ResendSMSResult.AWSError(errorPair.first, errorPair.second)
        exception.printStackTrace()
    }

    override fun onSuccess() {
        requestState.value = RequestState.Complete
        resendSMSResultLiveData.value = ResendSMSResult.Success
    }
}