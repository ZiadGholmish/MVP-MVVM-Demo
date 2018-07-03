package com.mystride.presentation.views.confirm

import android.arch.lifecycle.Observer
import android.content.Intent
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.mystride.app.AbsPresenter
import com.mystride.constatns.AppConstants
import com.mystride.data.remote.models.CreateUserResult
import com.mystride.data.remote.models.RequestState
import com.mystride.data.remote.models.ResendSMSResult
import com.mystride.presentation.views.phone.SignupPhoneViewModel
import javax.inject.Inject

class ConfirmSignUpPresenter @Inject constructor() : AbsPresenter<ConfirmSignUpController>() {

    private lateinit var confirmSignUpViewModel: ConfirmSignUpViewModel

    private lateinit var destination: String
    private lateinit var deliveryMedium: String
    private lateinit var attributeName: String
    private lateinit var phoneWithCode: String

    fun initPresenter(confirmSignUpViewModel: ConfirmSignUpViewModel, intent: Intent) {
        this.confirmSignUpViewModel = confirmSignUpViewModel
        getDataFromIntent(intent)
        showPhoneNumber()
        setObservers()
    }

    private fun getDataFromIntent(intent: Intent) {
        destination = intent.getStringExtra(AppConstants.DESTINATION_INTENT_NAME)
        deliveryMedium = intent.getStringExtra(AppConstants.DELIVERYMEDIUM_INTENT_NAME)
        attributeName = intent.getStringExtra(AppConstants.ATTRIBUTENAME_INTENT_NAME)
        phoneWithCode = intent.getStringExtra(AppConstants.PHONE_INTENT_NAME)

    }

    private fun showPhoneNumber() {
        mView?.showPhoneNumber(phoneWithCode)
    }

    fun resendSMS() {
        confirmSignUpViewModel.resendSMSCode()
    }

    fun confirmSMSCode(smsCode: String){
        confirmSignUpViewModel.confirmSMSCode(smsCode)
    }

    /**
     * set the observers for the live data for the request state, data and errors
     */
    private fun setObservers() {
        confirmSignUpViewModel.resendSMSResultLiveData.observe(mView!!, Observer {
            when (it) {
                is ResendSMSResult.Success -> mView?.showResendSuccess()
                is ResendSMSResult.AWSError -> mView?.showError(it.errorMessage)
            }
        })

        confirmSignUpViewModel.requestState.observe(mView!!, Observer {
            when (it!!) {
                is RequestState.Complete -> mView?.hideLoading()
                is RequestState.Idle -> mView?.hideLoading()
                is RequestState.Loading -> mView?.showLoading()
            }
        })
    }

}