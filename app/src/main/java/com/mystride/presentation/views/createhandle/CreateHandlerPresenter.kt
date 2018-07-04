package com.mystride.presentation.views.createhandle

import android.content.Intent
import com.mystride.app.AbsPresenter
import com.mystride.presentation.views.confirm.ConfirmSignUpViewModel
import javax.inject.Inject

class CreateHandlerPresenter @Inject constructor() : AbsPresenter<CreateHandlerController>() {

    private lateinit var createHandlerViewModel: CreateHandlerViewModel

    fun initPresenter(createHandlerViewModel: CreateHandlerViewModel) {
        this.createHandlerViewModel = createHandlerViewModel
    }

    fun checkHandler(handle: String) {
        createHandlerViewModel.setHandleName(handle)
    }

}