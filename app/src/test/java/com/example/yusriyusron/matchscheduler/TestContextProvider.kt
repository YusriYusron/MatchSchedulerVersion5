package com.example.yusriyusron.matchscheduler

import com.example.yusriyusron.matchscheduler.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlin.coroutines.CoroutineContext

class TestContextProvider: CoroutineContextProvider() {
    override val main:CoroutineContext = Unconfined
}