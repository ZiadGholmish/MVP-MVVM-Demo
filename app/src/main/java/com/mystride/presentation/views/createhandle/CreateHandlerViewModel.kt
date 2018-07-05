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
import com.mystride.data.remote.models.CreateHandleResult

class CreateHandlerViewModel @Inject constructor(val repository: Repository, val context: Context) : ViewModel() {

    val requestState = MutableLiveData<RequestState>()
    val createHandleResultLiveData = MutableLiveData<CreateHandleResult>()

    fun setHandleName(handleName: String) {

        if (AppHelper.user == null) {
            IllegalStateException("User can not be null")
            return
        }
        requestState.value = RequestState.Loading
        val userAttributes = createUSerAttributes(handleName)
        updateHandle(userAttributes)
    }

    /**
     * take the handle name(preferred_username) then return user attribute object
     */
    private fun createUSerAttributes(handleName: String): CognitoUserAttributes {
        val userAttributes = CognitoUserAttributes()
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_PREFERRED_USERNAME, handleName)
        return userAttributes
    }

    private fun updateHandle(userAttributes: CognitoUserAttributes) {
        repository.setHandleForTheUser(AppHelper.user!!, AppHelper.password!!, userAttributes, object : UpdateAttributesHandler {
            override fun onSuccess(attributesVerificationList: MutableList<CognitoUserCodeDeliveryDetails>?) {
                requestState.value = RequestState.Complete
                createHandleResultLiveData.value = CreateHandleResult.Success
            }

            override fun onFailure(exception: Exception) {
                requestState.value = RequestState.Complete
                val errorPair = AppHelper.formatException(exception)
                createHandleResultLiveData.value = CreateHandleResult.AWSError(errorPair.first, errorPair.second)
                exception.printStackTrace()
            }
        })
    }

}