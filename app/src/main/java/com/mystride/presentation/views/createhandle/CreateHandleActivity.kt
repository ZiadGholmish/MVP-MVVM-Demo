package com.mystride.presentation.views.createhandle

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mystride.app.MyStrideApp
import com.mystride.dagger.ViewModelFactory
import com.mystride.mystride.R
import com.mystride.presentation.views.confirm.ConfirmSignUpPresenter
import com.mystride.presentation.views.confirm.ConfirmSignUpViewModel
import com.mystride.presentation.views.home.HomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_create_handle_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.yesButton
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CreateHandleActivity : AppCompatActivity(), CreateHandlerController {

    @Inject
    lateinit var mPresenter: CreateHandlerPresenter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_handle_activity)
        initDependencyInjection()
        initActionBar()
        setButtonActions()
        addHandleObservable()
    }

    private fun initDependencyInjection() {
        (application as MyStrideApp).appComponent.inject(this)
        mPresenter.attachView(this)
        val createHandlerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateHandlerViewModel::class.java)
        mPresenter.initPresenter(createHandlerViewModel)
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
    }

    private fun setButtonActions() {
        btn_continue.setOnClickListener {
            startActivity(intentFor<HomeActivity>())
        }
    }

    override fun showHandleAlreadyTaken() {
        alert(getString(R.string.handle_already_taken_message), "") {
            yesButton {
                hideLoading()
                handle_layout.isErrorEnabled = true
                handle_layout.error = getString(R.string.handle_taken_error)
            }
        }.show()
    }

    override fun showHandleAvailable() {
        img_handle_available.visibility = View.VISIBLE
        btn_continue.isEnabled = true
    }

    override fun showLoading() {
        img_handle_available.visibility = View.INVISIBLE
        loading_view.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        img_handle_available.visibility = View.INVISIBLE
        loading_view.visibility = View.GONE
    }

    override fun showError(errorMessage: String) {
        alert(errorMessage, "") {
            yesButton {
                hideLoading()
            }
        }.show()
    }

    override fun showLimitError() {
        alert(getString(R.string.handle_request_limit_message), getString(R.string.whoa)) {
            yesButton {
                hideLoading()
            }
        }.show()
    }

    private fun addHandleObservable() {
        RxTextView.textChanges(handle_name)
                .debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe { inputText ->
                    if (inputText.length < 3) {
                        showHandleShort()
                    } else {
                        checkHandle(inputText.toString().trim())
                    }
                }
    }

    private fun showHandleShort() {
        handle_layout.isErrorEnabled = true
        handle_layout.error = getString(R.string.handle_short)
        img_handle_available.visibility = View.GONE
    }

    private fun checkHandle(handle: String) {
        handle_layout.isErrorEnabled = false
        mPresenter.checkHandler(handle)
    }


}
