package com.example.core_common

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val error: AppError): Result<Nothing>()
    data object Loading: Result<Nothing>()
}