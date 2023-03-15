package com.sharma.mymeal.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T?): Result<T>()
    data class Error<out T>(val error: String?): Result<T>()
}