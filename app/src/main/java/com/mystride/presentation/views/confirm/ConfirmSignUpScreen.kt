package com.mystride.presentation.views.confirm

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mystride.app.MyStrideApp
import com.mystride.dagger.ViewModelFactory
import com.mystride.data.remote.models.CountryModel
import com.mystride.mystride.R
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
        addSMSCodeObservable()
        setButtonActions()
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

    private fun addSMSCodeObservable() {
        RxTextView.textChanges(sms_code_edit)
                .map { inputText -> inputText.length > 5 }
                .subscribe { isValid ->
                    btn_continue.isEnabled = isValid
                }
    }


    private fun setButtonActions() {
        btn_resend.setOnClickListener {
            mPresenter.resendSMS()
        }
    }

}
