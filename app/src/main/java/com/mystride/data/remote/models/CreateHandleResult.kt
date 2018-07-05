package com.mystride.data.remote.models

sealed class CreateHandleResult {

    data class AWSError(val errorMessage: String, val errorCode: String) : CreateHandleResult()

    object Success : CreateHandleResult()
}