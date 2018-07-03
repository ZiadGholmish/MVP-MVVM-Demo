package com.mystride.presentation.views.phone

import com.mystride.app.AbsPresenter
import com.mystride.presentation.views.country.CountriesViewModel
import javax.inject.Inject

class SignupPhonePresenter @Inject constructor() : AbsPresenter<SignupPhoneController>() {

    private lateinit var signupPhoneViewModel: SignupPhoneViewModel

    fun initPresenter(signupPhoneViewModel: SignupPhoneViewModel) {
        this.signupPhoneViewModel = signupPhoneViewModel
    }

}