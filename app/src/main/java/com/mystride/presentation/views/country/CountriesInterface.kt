package com.mystride.presentation.views.country

import com.mystride.data.remote.models.CountryModel

interface CountriesInterface {

    fun onCountrySelected(countryModel: CountryModel)
}