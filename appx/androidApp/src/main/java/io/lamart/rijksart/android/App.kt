package io.lamart.rijksart.android

import android.app.Application
import androidx.activity.ComponentActivity
import io.lamart.rijksart.RijksMachine

class App : Application() {
    val machine by lazy { RijksMachine(this) }
}

val ComponentActivity.app: App
    get() = application.let { it as App }
