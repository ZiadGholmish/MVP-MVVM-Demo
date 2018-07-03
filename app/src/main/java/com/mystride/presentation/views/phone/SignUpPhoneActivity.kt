package com.mystride.presentation.views.phone

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mystride.app.MyStrideApp
import com.mystride.constatns.AppConstants
import com.mystride.dagger.ViewModelFactory
import com.mystride.data.remote.models.CountryModel
import com.mystride.mystride.R
import com.mystride.presentation.views.country.CountriesCodesActivity
import com.mystride.presentation.utils.phoneformatter.PhoneMaskWatcher
import com.mystride.presentation.views.confirm.ConfirmSignUpScreen
import kotlinx.android.synthetic.main.activity_sign_up_phone.*
import org.jetbrains.anko.*
import javax.inject.Inject

class SignUpPhoneActivity : AppCompatActivity(), SignupPhoneController {

    private val CHOOSE_COUNTRY_REQUEST_CODE = 100

    private lateinit var textWatcher: PhoneMaskWatcher

    @Inject
    lateinit var mPresenter: SignupPhonePresenter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_phone)
        initDependencyInjection()
        initActionBar()

        openCountriesCodeScreen()
        initPhoneNumber()
        openCountryCodePickList()
        createUserButtonActions()
    }

    private fun initDependencyInjection() {
        (application as MyStrideApp).appComponent.inject(this)
        mPresenter.attachView(this)
        val signupPhoneViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignupPhoneViewModel::class.java)
        mPresenter.initPresenter(signupPhoneViewModel, intent)
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            openCountryCodePickList()
        }
    }

    private fun addPhoneNumberObservable(selectedCountry: CountryModel) {
        RxTextView
                .textChanges(phone_number)
                .subscribe {
                    val isValidPhone = (textWatcher.phone.length == selectedCountry.number_length)
                    val isEmptyPhone = textWatcher.phone.isEmpty()
                    val isShortPhone = textWatcher.phone.length < selectedCountry.number_length

                    phone_number_layout.isErrorEnabled = !isValidPhone
                    btn_continue.isEnabled = isValidPhone

                    if (!isValidPhone && isEmptyPhone && phone_number.isEnabled) {
                        phone_number_layout.error = getString(R.string.please_enter_your_mobile_phone_number)
                    }

                    if (!isValidPhone && !isEmptyPhone && isShortPhone && phone_number.isEnabled) {
                        phone_number_layout.error = getString(R.string.please_enter_entire_number)
                    }
                }
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
                setSelectedCountry(it.getParcelableExtra(AppConstants.SELECTED_COUNTRY_INTENT_NAME))
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

    private fun openCountryCodePickList() {
        val intent = Intent(this, CountriesCodesActivity::class.java)
        startActivityForResult(intent, CHOOSE_COUNTRY_REQUEST_CODE)
    }

    private fun createUserButtonActions() {
        btn_continue.setOnClickListener {
            mPresenter.registerUser(textWatcher.phone, country_code.text.toString())
        }
    }

    override fun confirmSignUp(destination: String, deliveryMedium: String,
                               attributeName: String, phoneNumber: String) {
        val intent = Intent(this, ConfirmSignUpScreen::class.java)
        intent.putExtra(AppConstants.DESTINATION_INTENT_NAME, destination)
        intent.putExtra(AppConstants.DELIVERYMEDIUM_INTENT_NAME, deliveryMedium)
        intent.putExtra(AppConstants.ATTRIBUTENAME_INTENT_NAME, attributeName)
        intent.putExtra(AppConstants.PHONE_INTENT_NAME, phoneNumber)
        startActivity(intent)
    }

    override fun showError(errorMessage: String) {
        alert(errorMessage, "") {
            yesButton {
                hideLoading()
            }
        }.show()

    }

    override fun showLoading() {
        enableViews(false)
        loading_view.show()
    }

    override fun hideLoading() {
        enableViews(true)
        loading_view.hide()
    }

    override fun signUpSuccess() {
        alert(getString(R.string.signup_success_title), getString(R.string.signup_success_message)) {
            yesButton {
                finish()
            }
        }.show()
    }

    private fun enableViews(isEnabled: Boolean) {
        btn_continue.isEnabled = isEnabled
        phone_number.isEnabled = isEnabled
    }
}
