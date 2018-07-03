package com.mystride.presentation.views.confirm

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mystride.app.MyStrideApp
import com.mystride.dagger.ViewModelFactory
import com.mystride.mystride.R
import com.mystride.presentation.views.phone.SignupPhonePresenter
import com.mystride.presentation.views.phone.SignupPhoneViewModel
import kotlinx.android.synthetic.main.activity_confirm_sign_up_screen.*
import javax.inject.Inject

class ConfirmSignUpScreen : AppCompatActivity(), ConfirmSignUpController {


    @Inject
    lateinit var mPresenter: ConfirmSignUpPresenter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_sign_up_screen)
        initDependencyInjection()
        initActionBar()
    }


    private fun initDependencyInjection() {
        (application as MyStrideApp).appComponent.inject(this)
        mPresenter.attachView(this)
        val confirmSignUpViewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmSignUpViewModel::class.java)
        mPresenter.initPresenter(confirmSignUpViewModel, intent)
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showPhoneNumber(phoneNumber: String) {
        title = phoneNumber
    }
}
