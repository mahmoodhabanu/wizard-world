package com.example.core_common

fun AppError.toMessage(): String = when (this) {
    is AppError.Network -> "Check your internet connection."
    is AppError.HttpError -> "Error ${code}: $message"
    is AppError.Unexpected -> "Something went wrong: ${throwable.message}"
}