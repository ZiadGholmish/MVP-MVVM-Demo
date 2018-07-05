package com.mystride.presentation.views.landing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mystride.mystride.R
import com.mystride.presentation.views.createhandle.CreateHandleActivity
import com.mystride.presentation.views.signup.SignUpFirstLastNameActivity
import kotlinx.android.synthetic.main.activity_create_account.*
import org.jetbrains.anko.intentFor

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        openSignUp()
    }

    private fun openSignUp() {
        btn_sign_up.setOnClickListener {
            startActivity(intentFor<SignUpFirstLastNameActivity>())
        }
    }
}
