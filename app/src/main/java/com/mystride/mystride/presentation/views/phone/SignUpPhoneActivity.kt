package com.mystride.mystride.presentation.views.phone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.github.vacxe.phonemask.PhoneMaskManager
import com.mystride.mystride.R
import com.mystride.mystride.presentation.views.country.CountriesCodesActivity
import kotlinx.android.synthetic.main.activity_sign_up_phone.*

class SignUpPhoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_phone)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setPhoneMask()
        openCountriesCodeScreen()
    }

    private fun setPhoneMask() {
        PhoneMaskManager()
                .withMask("(###) ###-###-###")
                .withValueListener { }
                .bindTo(phone_number)
    }

    private fun openCountriesCodeScreen() {
        country_name.setOnClickListener {
            val intent = Intent(this, CountriesCodesActivity::class.java)
            startActivity(intent)
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

}
