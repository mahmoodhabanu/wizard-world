package com.example.core_common

import retrofit2.HttpException
import java.io.IOException

sealed class AppError {
    data object Network : AppError()
    data class HttpError(val code: Int, val message: String) : AppError()
    data class Unexpected(val throwable: Throwable) : AppError()

    companion object {
        fun fromException(e: Throwable): AppError = when (e) {
            is IOException -> Network
            is HttpException -> HttpError(e.code(), e.message())
            else -> Unexpected(e)
        }
    }
}