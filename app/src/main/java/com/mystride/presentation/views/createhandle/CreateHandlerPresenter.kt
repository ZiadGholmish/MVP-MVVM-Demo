package com.mystride.presentation.views.createhandle

import android.arch.lifecycle.Observer
import android.content.Intent
import com.mystride.app.AbsPresenter
import com.mystride.constatns.UserPoolConstants
import com.mystride.data.remote.models.ConfirmCodeResult
import com.mystride.data.remote.models.CreateHandleResult
import com.mystride.data.remote.models.RequestState
import com.mystride.data.remote.models.ResendSMSResult
import com.mystride.presentation.views.confirm.ConfirmSignUpViewModel
import javax.inject.Inject

class CreateHandlerPresenter @Inject constructor() : AbsPresenter<CreateHandlerController>() {

    private lateinit var createHandlerViewModel: CreateHandlerViewModel
    var handle = ""

    fun initPresenter(createHandlerViewModel: CreateHandlerViewModel) {
        this.createHandlerViewModel = createHandlerViewModel
        setObservers()
    }

    fun checkHandler(handle: String) {
        this.handle = handle
        createHandlerViewModel.setHandleName(handle)
    }

    /**
     * set the observers for the live data for the request state, data and errors
     */
    private fun setObservers() {
        createHandlerViewModel.createHandleResultLiveData.observe(mView!!, Observer {
            when (it) {
                is CreateHandleResult.Success -> mView?.showHandleAvailable()
                is CreateHandleResult.AWSError -> handleErrors(it.errorMessage, it.errorCode, 0)
            }
        })
        createHandlerViewModel.requestState.observe(mView!!, Observer {
            when (it!!) {
                is RequestState.Complete -> mView?.hideLoading()
                is RequestState.Idle -> mView?.hideLoading()
                is RequestState.Loading -> mView?.showLoading()
            }
        })
    }

    private fun handleErrors(errorMessage: String, errorCode: String, source: Int) {
        when (errorCode) {
            UserPoolConstants.LIMIT_EXCEEDED_EXCEPTION -> mView?.showLimitError()
            UserPoolConstants.ALIAS_EXISTS_EXCEPTION -> mView?.showHandleAlreadyTaken()
            else -> mView?.showError(errorMessage)
        }
    }

}