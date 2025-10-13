package com.example.core_common

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Error(val error: AppError) : UIState<Nothing>()
    data object Idle : UIState<Nothing>() // Initial state
}