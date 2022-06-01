package com.phisit.weatherforecast.common.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatcherProvider {
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}

class DefaultCoroutineDispatcherProvider : CoroutineDispatcherProvider {
    override fun main(): CoroutineDispatcher = Dispatchers.Main
    override fun default(): CoroutineDispatcher = Dispatchers.Default
    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class TestCoroutineDispatcherProvider(
    private val dispatcher: CoroutineDispatcher
) : CoroutineDispatcherProvider {
    override fun main(): CoroutineDispatcher = dispatcher
    override fun default(): CoroutineDispatcher = dispatcher
    override fun io(): CoroutineDispatcher = dispatcher
    override fun unconfined(): CoroutineDispatcher = dispatcher
}