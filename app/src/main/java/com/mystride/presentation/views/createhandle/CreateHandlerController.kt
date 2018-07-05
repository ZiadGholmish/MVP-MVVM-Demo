package com.mystride.presentation.views.createhandle

import android.arch.lifecycle.LifecycleOwner

interface CreateHandlerController : LifecycleOwner {

    fun showHandleAlreadyTaken()

    fun showHandleAvailable()

    fun showLoading()

    fun hideLoading()

    fun showError(errorMessage: String)

    fun showLimitError()
}