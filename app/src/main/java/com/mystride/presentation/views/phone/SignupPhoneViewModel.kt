package com.mystride.presentation.views.phone

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.mystride.constatns.AppConstants
import com.mystride.constatns.UserPoolConstants
import com.mystride.data.remote.models.CountryModel
import com.mystride.data.remote.models.CreateUserResult
import com.mystride.data.remote.models.RequestState
import com.mystride.data.repository.Repository
import com.mystride.presentation.utils.AppHelper
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class SignupPhoneViewModel @Inject constructor(val repository: Repository) : ViewModel(), SignUpHandler {

    val userResultLiveData = MutableLiveData<CreateUserResult>()
    val requestState = MutableLiveData<RequestState>()
    private lateinit var phoneWithCode: String

    fun createUser(firstName: String, lastName: String, phone: String, countryCode: String) {
        requestState.value = RequestState.Loading
        val userAttributes = createUserAttributes(firstName, lastName, phone, countryCode)
        repository.registerUser(AppHelper.generateUserId(), AppHelper.generateRandomPassword(), userAttributes, this)
    }

    /**
     * format the form data and create user attributes
     */
    private fun createUserAttributes(firstName: String, lastName: String, phone: String, countryCode: String): CognitoUserAttributes {
        val userAttributes = CognitoUserAttributes()
        phoneWithCode = "$countryCode$phone"
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_EMAIL, "ziad@trycarriage.com")
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_PHONE_NUMBER, phoneWithCode)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_GIVEN_NAME, firstName)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_FAMILY_NAME, lastName)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_FAMILY_NAME, lastName)
        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_LOCALE, UserPoolConstants.LOCALE_US)
        return userAttributes
    }

    override fun onSuccess(user: CognitoUser?, signUpConfirmationState: Boolean,
                           cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?) {
        requestState.value = RequestState.Complete
        if (signUpConfirmationState) {
            userResultLiveData.value = CreateUserResult.Success
        } else {
            if (cognitoUserCodeDeliveryDetails == null) {
                val errorPair = AppHelper.formatException(IllegalStateException("Error in create user"))
                userResultLiveData.value = CreateUserResult.AWSError(errorPair.first)
            }
            cognitoUserCodeDeliveryDetails?.run {
                userResultLiveData.value = CreateUserResult.Verification(destination, deliveryMedium, attributeName, phoneWithCode)
            }
        }
    }

    override fun onFailure(exception: Exception) {
        userResultLiveData.value = CreateUserResult.AWSError(AppHelper.formatException(exception).first)
        exception.printStackTrace()
    }
}