package com.mystride.dagger

import com.mystride.presentation.views.country.CountriesCodesActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(countriesCodesActivity: CountriesCodesActivity)

}