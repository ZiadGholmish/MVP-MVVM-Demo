package com.mystride.mystride.presentation.views.landing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mystride.mystride.R
import com.mystride.mystride.presentation.views.signup.SignUpFirstLastNameActivity
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        openSignUp()
    }

    private fun openSignUp() {
        btn_sign_up.setOnClickListener {
            val intent = Intent(this, SignUpFirstLastNameActivity::class.java)
            startActivity(intent)
        }
    }
}
