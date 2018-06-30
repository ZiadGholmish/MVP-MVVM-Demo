package com.mystride.mystride.presentation.views.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mystride.mystride.R
import com.mystride.mystride.presentation.views.phone.SignUpPhoneActivity
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_sign_up_first_last_name.*

class SignUpFirstLastNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_first_last_name)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        openPhoneActivity()
    }

    private fun openPhoneActivity() {
        btn_continue.setOnClickListener {
            val intent = Intent(this, SignUpPhoneActivity::class.java)
            startActivity(intent)
        }
    }

}
