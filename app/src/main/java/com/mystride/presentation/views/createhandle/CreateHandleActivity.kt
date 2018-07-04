package com.mystride.presentation.views.createhandle

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mystride.app.MyStrideApp
import com.mystride.dagger.ViewModelFactory
import com.mystride.mystride.R
import com.mystride.presentation.views.confirm.ConfirmSignUpPresenter
import com.mystride.presentation.views.confirm.ConfirmSignUpViewModel
import kotlinx.android.synthetic.main.activity_create_handle_activity.*
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
            mPresenter.checkHandler(handle_name.text.toString().trim())
        }
    }
}
