package io.lamart.rijksart

import android.app.Application
import io.lamart.rijksart.logic.Logic
import io.lamart.rijksart.logic.logicOf
import io.lamart.rijksart.rijksmuseum.rijksMuseumOf
import io.lamart.rijksart.services.servicesOf
import kotlinx.coroutines.MainScope

class RijksApplication : Application() {

    lateinit var logic: Logic

    override fun onCreate() {
        super.onCreate()
        val scope = MainScope()
        val museum = rijksMuseumOf("0fiuZFh4")
        val services = servicesOf(this)

        logic = logicOf(museum, services, scope)

        logic.actions.appendCollection()
    }

}

val RijksActivity.rijksApplication: RijksApplication
    get() = application as RijksApplication