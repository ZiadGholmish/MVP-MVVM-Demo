package com.mystride.presentation.views.confirm

import android.arch.lifecycle.LifecycleOwner

interface ConfirmSignUpController : LifecycleOwner {

    fun showPhoneNumber(phoneNumber: String)

    fun showError(errorMessage: String)

    fun showCodeRequestLimitError()

    fun showCodeMismatchError()

    fun showCodeWrongLimitError()

    fun showUserAlreadyConfirmed()

    fun showResendSuccess()

    fun showLoading()

    fun hideLoading()

}