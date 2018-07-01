package com.mystride.presentation.views.country

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.mystride.app.MyStrideApp
import com.mystride.constatns.AppConstant
import com.mystride.dagger.ViewModelFactory
import com.mystride.data.remote.models.CountryModel
import com.mystride.mystride.R
import kotlinx.android.synthetic.main.activity_county_codes.*
import javax.inject.Inject

class CountriesCodesActivity : AppCompatActivity(), CountriesCodeController, CountriesInterface {


    @Inject
    lateinit var mPresenter: CountriesCodesPresenter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_county_codes)
        initDependencyInjection()
        initActionBar()
        initCountriesRecycler()
    }

    private fun initDependencyInjection() {
        (application as MyStrideApp).appComponent.inject(this)
        mPresenter.attachView(this)
        val countriesViewModel = ViewModelProviders.of(this, viewModelFactory).get(CountriesViewModel::class.java)
        mPresenter.initPresenter(countriesViewModel)
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initCountriesRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        countries_recycler.layoutManager = linearLayoutManager
    }

    override fun showCountriesCodeList(countriesList: List<CountryModel>) {
        val countriesCodeAdapter = CountriesCodeAdapter(countriesList, this)
        countries_recycler.adapter = countriesCodeAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun onCountrySelected(selectedCountry: CountryModel) {
        val intent = Intent()
        intent.putExtra(AppConstant.SELECTED_COUNTRY_INTENT_NAME, selectedCountry)
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }
}
