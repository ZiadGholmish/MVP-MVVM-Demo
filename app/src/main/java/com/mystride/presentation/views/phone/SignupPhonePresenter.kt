package com.mystride.presentation.views.phone

import android.arch.lifecycle.Observer
import android.content.Intent
import android.util.Log
import com.mystride.app.AbsPresenter
import com.mystride.constatns.AppConstants
import com.mystride.data.remote.models.CreateUserResult
import com.mystride.data.remote.models.RequestState
import com.mystride.presentation.views.country.CountriesViewModel
import javax.inject.Inject

class SignupPhonePresenter @Inject constructor() : AbsPresenter<SignupPhoneController>() {

    private lateinit var signupPhoneViewModel: SignupPhoneViewModel
    private lateinit var firstName: String
    private lateinit var lastName: String

    fun initPresenter(signupPhoneViewModel: SignupPhoneViewModel, intent: Intent) {
        this.signupPhoneViewModel = signupPhoneViewModel
        setObservers()
        getFormData(intent)
    }

    /**
     * fire the action to create use in aws user pool
     */
    fun registerUser(phone: String, countryCode: String) {
        signupPhoneViewModel.createUser(firstName, lastName, phone, countryCode)
    }

    /**
     * set the observers for the live data for the request state, data and errors
     */
    private fun setObservers() {
        signupPhoneViewModel.userResultLiveData.observe(mView!!, Observer {
            when (it) {
                is CreateUserResult.Success -> mView?.signUpSuccess()
                is CreateUserResult.AWSError -> mView?.showError(it.errorMessage)
                is CreateUserResult.Verification -> confirmSignUp(it)
            }
        })

        signupPhoneViewModel.requestState.observe(mView!!, Observer {
            when (it!!) {
                is RequestState.Complete -> mView?.hideLoading()
                is RequestState.Idle -> mView?.hideLoading()
                is RequestState.Loading -> mView?.showLoading()
            }
        })
    }

    /**
     * get the first and last name from the intent and assign it to the variables
     *
     */
    private fun getFormData(intent: Intent) {
        firstName = intent.getStringExtra(AppConstants.FIRST_NAME_INTENT_NAME)
        lastName = intent.getStringExtra(AppConstants.LAST_NAME_INTENT_NAME)
    }

    /**
     * send the user to the confirm sms code screen
     */
    private fun confirmSignUp(userResultVerification: CreateUserResult.Verification) {
        mView?.confirmSignUp(userResultVerification.destination,
                userResultVerification.deliveryMedium,
                userResultVerification.attributeName ,
                userResultVerification.phoneNumber
                )
    }

}