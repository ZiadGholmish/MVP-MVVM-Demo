package com.mystride.presentation.views.country

import android.arch.lifecycle.LifecycleOwner
import com.mystride.data.remote.models.CountryModel

interface CountriesCodeController : LifecycleOwner {

    fun showCountriesCodeList(countriesList: List<CountryModel>)

    fun showLoading()

    fun hideLoading()

}