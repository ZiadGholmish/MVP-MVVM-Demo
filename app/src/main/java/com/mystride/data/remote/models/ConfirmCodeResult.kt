package com.mystride.data.remote.models

sealed class ConfirmCodeResult {

    data class AWSError(val errorMessage: String , val errorCode: String) : ConfirmCodeResult()

    object Success : ConfirmCodeResult()
}