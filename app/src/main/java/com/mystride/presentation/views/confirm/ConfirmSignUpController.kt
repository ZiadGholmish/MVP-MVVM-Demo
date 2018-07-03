package com.mystride.presentation.views.confirm

import android.arch.lifecycle.LifecycleOwner

interface ConfirmSignUpController : LifecycleOwner{

    fun showPhoneNumber(phoneNumber: String)

}