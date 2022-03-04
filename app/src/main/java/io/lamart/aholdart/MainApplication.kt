package io.lamart.aholdart

import android.app.Application
import io.lamart.aholdart.logic.logicOf
import io.lamart.aholdart.rijksmuseum.rijksMuseumOf
import io.lamart.aholdart.services.servicesOf
import kotlinx.coroutines.MainScope

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val scope = MainScope()
        val museum = rijksMuseumOf("0fiuZFh4")
        val services = servicesOf(this)
        val logic = logicOf(museum, services, scope)

    }

}