package com.guri.guriquizzy

import android.app.Application
import com.guri.guriquizzy.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class GuriQuizzyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.initialize(this)
    }
}