package com.mystride.presentation.views.phone

import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import javax.inject.Inject

class SignupPhoneViewModel @Inject constructor(val userPool: CognitoUserPool) : ViewModel() {


}