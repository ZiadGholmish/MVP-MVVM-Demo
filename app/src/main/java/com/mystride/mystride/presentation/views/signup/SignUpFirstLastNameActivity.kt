package com.mystride.mystride.presentation.views.signup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mystride.mystride.R
import kotlinx.android.synthetic.main.activity_sign_up_first_last_name.*

class SignUpFirstLastNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_first_last_name)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
