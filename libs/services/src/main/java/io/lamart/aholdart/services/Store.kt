package io.lamart.aholdart.services

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

internal class Store(private val context: Context) {

    private val Context.store: DataStore<Preferences> by preferencesDataStore(name = "data")

    suspend fun <T> get(key: Preferences.Key<T>): T? =
        context
            .store
            .data
            .map { it[key] }
            .firstOrNull()

    suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        context
            .store
            .edit { settings -> settings[key] = value }
    }

}