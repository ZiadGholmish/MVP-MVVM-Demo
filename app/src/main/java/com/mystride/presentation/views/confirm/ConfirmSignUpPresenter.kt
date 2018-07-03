package com.mystride.presentation.views.confirm

import android.content.Intent
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.mystride.app.AbsPresenter
import com.mystride.constatns.AppConstants
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

    fun resendSMS(){
        confirmSignUpViewModel.resendSMSCode()
    }

}