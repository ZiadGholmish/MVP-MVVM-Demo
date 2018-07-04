package com.mystride.dagger

import com.mystride.presentation.views.confirm.ConfirmSignUpActivity
import com.mystride.presentation.views.country.CountriesCodesActivity
import com.mystride.presentation.views.createhandle.CreateHandleActivity
import com.mystride.presentation.views.createhandle.CreateHandlerViewModel
import com.mystride.presentation.views.phone.SignUpPhoneActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(countriesCodesActivity: CountriesCodesActivity)

    fun inject(phoneSignUpPhoneActivity: SignUpPhoneActivity)

    fun inject(confirmSignUpScreen: ConfirmSignUpActivity)

    fun inject(createHandleActivity: CreateHandleActivity)
}