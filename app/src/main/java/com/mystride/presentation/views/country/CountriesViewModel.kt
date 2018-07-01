package com.mystride.presentation.views.country

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mystride.data.remote.models.CountryModel
import com.mystride.data.remote.models.RequestState
import com.mystride.data.repository.Repository
import javax.inject.Inject

class CountriesViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val countriesLiveData = MutableLiveData<List<CountryModel>>()
    val requestState = MutableLiveData<RequestState>()

    fun getCountriesList() {
        repository.getCountriesList()
                .doOnError {
                    requestState.value = RequestState.Complete
                    it.printStackTrace()
                }
                .doOnSubscribe { requestState.value = RequestState.Loading }
                .doOnSuccess { requestState.value = RequestState.Complete }
                .subscribe({ countriesList ->
                    countriesLiveData.value = countriesList
                })
    }


}