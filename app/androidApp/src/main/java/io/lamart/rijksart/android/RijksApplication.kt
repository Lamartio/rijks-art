package io.lamart.rijksart.android

import android.app.Application
import io.lamart.rijksart.Platform
import io.lamart.rijksart.logic.toMachine

class RijksApplication : Application() {
    val machine by lazy { Platform(this).toMachine() }

    override fun onCreate() {
        super.onCreate()
        machine.actions.initialize()
    }
}