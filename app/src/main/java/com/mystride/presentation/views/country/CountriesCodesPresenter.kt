package com.mystride.presentation.views.country

import android.arch.lifecycle.Observer
import com.mystride.app.AbsPresenter
import com.mystride.data.remote.models.RequestState
import javax.inject.Inject

class CountriesCodesPresenter @Inject constructor() : AbsPresenter<CountriesCodeController>() {

    private lateinit var countriesViewModel: CountriesViewModel

    fun initPresenter(countriesViewModel: CountriesViewModel) {
        this.countriesViewModel = countriesViewModel
        setObservers()
        countriesViewModel.getCountriesList()
    }

    /**
     * set the observers for the live data for the request state, data and errors
     */
    private fun setObservers() {
        countriesViewModel.countriesLiveData.observe(mView!!, Observer {
            mView?.showCountriesCodeList(it.let { it } ?: listOf())
        })

        countriesViewModel.requestState.observe(mView!!, Observer {
            when (it!!) {
                is RequestState.Complete -> mView?.hideLoading()
                is RequestState.Idle -> mView?.hideLoading()
                is RequestState.Loading -> mView?.showLoading()
            }
        })
    }

}