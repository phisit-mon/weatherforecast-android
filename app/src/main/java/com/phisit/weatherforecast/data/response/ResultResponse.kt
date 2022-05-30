package com.phisit.weatherforecast.data.response

sealed class ResultResponse<out T : Any>

class Success<out T : Any>(val data: T) : ResultResponse<T>()

class Failure(val exception: Throwable) : ResultResponse<Nothing>()