package com.mystride.mystride.presentation.views.phone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.github.vacxe.phonemask.PhoneMaskManager
import com.github.vacxe.phonemask.ValueListener
import com.mystride.mystride.R
import com.mystride.mystride.presentation.views.country.CountyCodesActivity
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
            val intent = Intent(this, CountyCodesActivity::class.java)
            startActivity(intent)
        }
    }
}
