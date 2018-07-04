package com.mystride.presentation.views.createhandle

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler
import com.mystride.constatns.UserPoolConstants
import com.mystride.data.remote.models.RequestState
import com.mystride.data.repository.Repository
import com.mystride.presentation.utils.AppHelper
import java.lang.Exception
import javax.inject.Inject
import com.amazonaws.regions.Regions
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.auth.CognitoCredentialsProvider
import com.amazonaws.mobileconnectors.cognitoidentityprovider.util.CognitoIdentityProviderClientConfig
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler


class CreateHandlerViewModel @Inject constructor(val repository: Repository, val context: Context) : ViewModel(), UpdateAttributesHandler {

    val requestState = MutableLiveData<RequestState>()

    fun setHandleName(handleName: String) {
        requestState.value = RequestState.Loading
        if (AppHelper.user == null) {
            IllegalStateException("User can not be null")
            return
        }
        val userAttributes = CognitoUserAttributes()
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_PREFERRED_USERNAME, handleName)
        repository.setHandleForTheUser(AppHelper.user!!,AppHelper.password!!, userAttributes, this)
    }


    override fun onSuccess(attributesVerificationList: MutableList<CognitoUserCodeDeliveryDetails>?) {
        attributesVerificationList
    }

    override fun onFailure(exception: Exception?) {
        exception?.printStackTrace()
    }
}