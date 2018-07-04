package com.mystride.data.remote.models

sealed class ResendSMSResult {

    data class AWSError(val errorMessage: String , val errorCode: String) : ResendSMSResult()

    object Success : ResendSMSResult()

}