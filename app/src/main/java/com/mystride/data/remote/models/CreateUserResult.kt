package com.mystride.data.remote.models

import java.lang.Exception

sealed class CreateUserResult {

    data class Verification(val destination: String,
                            val deliveryMedium: String, val
                            attributeName: String) : CreateUserResult()

    data class AWSError(val errorMessage: String) : CreateUserResult()

    object Success : CreateUserResult()

}