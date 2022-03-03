package io.lamart.aholdart.services

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.lamart.aholdart.domain.ArtDetails
import io.lamart.aholdart.domain.decode
import io.lamart.aholdart.domain.encode
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface Services {

    suspend fun getDetails(): ArtDetails?

    suspend fun setDetails(details: ArtDetails)

}

fun servicesOf(context: Context): Services {
    return object : Services {

        private val Context.store: DataStore<Preferences> by preferencesDataStore(name = "data")
        val DETAILS = stringPreferencesKey("details")

        override suspend fun getDetails(): ArtDetails? =
            context
                .store
                .data
                .map { it[DETAILS] }
                .firstOrNull()
                ?.let { ArtDetails.serializer().decode(it) }

        override suspend fun setDetails(details: ArtDetails) {
            context
                .store
                .edit { settings -> settings[DETAILS] = ArtDetails.serializer().encode(details) }
        }

    }
}
