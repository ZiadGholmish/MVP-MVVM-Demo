package com.mystride.presentation.views.phone

import android.arch.lifecycle.LifecycleOwner

interface SignupPhoneController : LifecycleOwner {

    fun confirmSignUp(destination: String, deliveryMedium: String,
                      attributeName: String, phoneNumber: String)

    fun showError(errorMessage: String)

    fun signUpSuccess()

    fun showLoading()

    fun hideLoading()

}