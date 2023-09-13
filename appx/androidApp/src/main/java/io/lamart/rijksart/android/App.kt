package io.lamart.rijksart.android

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import io.lamart.rijksart.Platform
import io.lamart.rijksart.logic.toMachine

class App : Application() {
    val machine by lazy { Platform(this).toMachine() }

    override fun onCreate() {
        super.onCreate()
        machine.actions.initialize()
    }
}

val ComponentActivity.app: App
    get() = application.let { it as App }

val Context.app: App
    get() = applicationContext.let { it as App }