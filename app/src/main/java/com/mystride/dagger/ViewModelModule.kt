package com.mystride.dagger

import android.arch.lifecycle.ViewModel
import com.mystride.presentation.views.confirm.ConfirmSignUpViewModel
import com.mystride.presentation.views.country.CountriesViewModel
import com.mystride.presentation.views.createhandle.CreateHandlerViewModel
import com.mystride.presentation.views.phone.SignupPhoneViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesListViewModel(countriesViewModel: CountriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupPhoneViewModel::class)
    abstract fun bindSignupPhoneViewModel(SignupPhoneViewModel: SignupPhoneViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmSignUpViewModel::class)
    abstract fun bindConfirmSignUpViewModel(confirmSignUpViewModel: ConfirmSignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateHandlerViewModel::class)
    abstract fun bindCreateHandlerViewModel(createHandlerViewModel: CreateHandlerViewModel): ViewModel
}