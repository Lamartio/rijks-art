package io.lamart.aholdart.services

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey

interface Services {

    suspend fun getDetails(): String?

    suspend fun setDetails(details: String)

}

fun servicesOf(context: Context): Services {
    return object : Services {

        val store = Store(context)
        val DETAILS = stringPreferencesKey("details")

        override suspend fun getDetails(): String? = store.get(DETAILS)

        override suspend fun setDetails(details: String) = store.set(DETAILS, details)

    }
}
