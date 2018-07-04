package com.mystride.presentation.views.createhandle

import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.mystride.constatns.UserPoolConstants
import javax.inject.Inject

class CreateHandlerViewModel @Inject constructor() : ViewModel() {



    fun setHandleName(handleName: String){

        val userAttributes = CognitoUserAttributes()

        userAttributes.addAttribute(UserPoolConstants.COGINTO_USER_ATTRIBUTES_EMAIL, "ziadgholmish@gmail.com")

    }


}