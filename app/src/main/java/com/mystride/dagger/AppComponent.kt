package com.mystride.dagger

import com.mystride.presentation.views.confirm.ConfirmSignUpScreen
import com.mystride.presentation.views.confirm.ConfirmSignUpViewModel
import com.mystride.presentation.views.country.CountriesCodesActivity
import com.mystride.presentation.views.phone.SignUpPhoneActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(countriesCodesActivity: CountriesCodesActivity)

    fun inject(phoneSignUpPhoneActivity: SignUpPhoneActivity)

    fun inject(confirmSignUpScreen: ConfirmSignUpScreen)
}