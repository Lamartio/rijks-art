package io.lamart.aholdart.services

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.lamart.aholdart.domain.ArtCollection
import io.lamart.aholdart.domain.ArtDetails

interface Storage {

    suspend fun getCollection(page: Int): ArtCollection?

    suspend fun setCollection(page: Int, collection: ArtCollection)

    suspend fun getDetails(objectNumber: String): ArtDetails?

    suspend fun setDetails(objectNumber: String, details: ArtDetails)
}

internal fun storageOf(context: Context): Storage =
    object : Storage, StorageMixin by storageMixinOf(context, "data") {

        override suspend fun getCollection(page: Int): ArtCollection? =
            get(collectionKeyOf(page), ArtCollection.serializer())

        override suspend fun setCollection(page: Int, collection: ArtCollection) =
            set(collectionKeyOf(page), ArtCollection.serializer(), collection)

        override suspend fun getDetails(objectNumber: String): ArtDetails? =
            get(detailsKeyOf(objectNumber), ArtDetails.serializer())

        override suspend fun setDetails(objectNumber: String, details: ArtDetails) =
            set(detailsKeyOf(objectNumber), ArtDetails.serializer(), details)

        private fun collectionKeyOf(page: Int): Preferences.Key<String> =
            stringPreferencesKey("collection_$page")

        private fun detailsKeyOf(objectNumber: String): Preferences.Key<String> =
            stringPreferencesKey("details_$objectNumber")


    }