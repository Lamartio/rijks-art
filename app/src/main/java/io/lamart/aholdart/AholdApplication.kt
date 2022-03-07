package io.lamart.aholdart

import android.app.Application
import io.lamart.aholdart.logic.Logic
import io.lamart.aholdart.logic.logicOf
import io.lamart.aholdart.rijksmuseum.rijksMuseumOf
import io.lamart.aholdart.services.servicesOf
import kotlinx.coroutines.MainScope

class AholdApplication : Application() {

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

val AholdActivity.aholdApplication: AholdApplication
    get() = application as AholdApplication