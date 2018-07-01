package com.mystride.presentation.views.phone

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mystride.constatns.AppConstant
import com.mystride.data.remote.models.CountryModel
import com.mystride.mystride.R
import com.mystride.presentation.views.country.CountriesCodesActivity
import com.mystride.presentation.views.utils.PhoneMaskWatcher
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_sign_up_phone.*

class SignUpPhoneActivity : AppCompatActivity() {

    val CHOOSE_COUNTRY_REQUEST_CODE = 100

    lateinit var textWatcher: PhoneMaskWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_phone)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        openCountriesCodeScreen()
        initPhoneNumber()

    }

    private fun setSelectedCountry(selectedCountry: CountryModel) {
        addPhoneNumberObservable(selectedCountry)
        showSelectedCountryDetails(selectedCountry)
        setPhoneMask(selectedCountry)
    }


    private fun initPhoneNumber() {
        textWatcher = PhoneMaskWatcher("(###) ####-####", phone_number)
        phone_number.addTextChangedListener(textWatcher)
    }

    private fun setPhoneMask(selectedCountry: CountryModel) {
        textWatcher.mask = selectedCountry.mask
    }

    private fun openCountriesCodeScreen() {
        country_name.setOnClickListener {
            val intent = Intent(this, CountriesCodesActivity::class.java)
            startActivityForResult(intent, CHOOSE_COUNTRY_REQUEST_CODE)
        }
    }

    private fun addPhoneNumberObservable(selectedCountry: CountryModel): Observable<Boolean> {
        phone_number_layout.error = getString(R.string.please_enter_your_mobile_phone_number)
        val phoneNumberObservable = RxTextView
                .textChanges(phone_number)
                .map { textWatcher.phone.length - 1 == selectedCountry.number_length }
                .distinctUntilChanged()
        phoneNumberObservable.subscribe { isValidPhone ->
            phone_number_layout.isErrorEnabled = !isValidPhone
            btn_continue.isEnabled = isValidPhone
            if (!isValidPhone) {
                phone_number_layout.error = getString(R.string.please_enter_your_mobile_phone_number)
            }
        }
        return phoneNumberObservable
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHOOSE_COUNTRY_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let {
                setSelectedCountry(it.getParcelableExtra(AppConstant.SELECTED_COUNTRY_INTENT_NAME))
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showSelectedCountryDetails(selectedCountry: CountryModel) {
        isCountrySupported(selectedCountry.supported)
        country_name.setText(selectedCountry.name)
        country_code.setText(selectedCountry.dial_code)
        phone_number.setText("")
        phone_number.hint = selectedCountry.mask_hint
    }

    private fun isCountrySupported(supported: Boolean) {
        phone_number_layout.isErrorEnabled = supported
        phone_number.isEnabled = supported
        country_code_layout.isErrorEnabled = !supported

        if (supported) {
            error_view.visibility = View.GONE
        } else {
            error_view.visibility = View.VISIBLE
            country_code_layout.error = " "
        }
    }

}
