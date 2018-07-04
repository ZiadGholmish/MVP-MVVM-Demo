package com.mystride.presentation.views.confirm

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mystride.app.MyStrideApp
import com.mystride.dagger.ViewModelFactory
import com.mystride.mystride.R
import com.mystride.presentation.views.createhandle.CreateHandleActivity
import com.mystride.presentation.views.landing.CreateAccountActivity
import kotlinx.android.synthetic.main.activity_confirm_sign_up_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.yesButton
import javax.inject.Inject

class ConfirmSignUpActivity : AppCompatActivity(), ConfirmSignUpController {

    @Inject
    lateinit var mPresenter: ConfirmSignUpPresenter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_sign_up_activity)
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
                    sms_code_layout.isErrorEnabled = !isValid
                    if (!isValid) {
                        sms_code_layout.error = getString(R.string.sms_code_short)
                    }
                }
    }

    private fun setButtonActions() {
        btn_resend.setOnClickListener {
            mPresenter.resendSMS()
        }

        btn_continue.setOnClickListener {
            mPresenter.confirmSMSCode(sms_code_edit.text.toString().trim())
        }
    }

    override fun showError(errorMessage: String) {
        alert(errorMessage, "") {
            yesButton {
                hideLoading()
            }
        }.show()
    }

    override fun showResendSuccess() {
        Snackbar.make(sms_code_layout, getString(R.string.resend_success_message), Snackbar.LENGTH_LONG).show()
    }

    override fun showLoading() {
        group.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        group.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
    }

    override fun showCodeRequestLimitError() {
        alert(getString(R.string.code_request_limit_message), getString(R.string.whoa)) {
            yesButton {
                hideLoading()
            }
        }.show()
    }

    override fun showCodeMismatchError() {
        alert(getString(R.string.invalid_code_message), getString(R.string.invalid_code)) {
            yesButton {
                hideLoading()
            }
        }.show()
    }

    override fun showCodeWrongLimitError() {
        alert(getString(R.string.too_many_wrong_codes), getString(R.string.break_time)) {
            yesButton {
                hideLoading()
            }
        }.show()
    }

    override fun showUserAlreadyConfirmed() {
        alert(getString(R.string.whould_you_like_to_login), getString(R.string.good_news)) {
            positiveButton(getString(R.string.yes)) {
                startActivity(intentFor<CreateAccountActivity>().singleTop())
            }

            negativeButton(getString(R.string.no_thanks)) {
                finish()
            }
        }.show()
    }

    override fun showConfirmedSuccess() {
        startActivity(intentFor<CreateHandleActivity>())
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
