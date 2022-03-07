package io.lamart.aholdart.services

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import io.lamart.aholdart.domain.ArtCollection
import io.lamart.aholdart.domain.ArtDetails

interface Storage {

    suspend fun getCollection(page: Int): ArtCollection?

    suspend fun setCollection(page: Int, collection: ArtCollection)

    suspend fun getDetails(): ArtDetails?

    suspend fun setDetails(details: ArtDetails)
}

internal fun storageOf(context: Context): Storage =
    object : Storage, StorageMixin by storageMixinOf(context, "data") {

        val DETAILS = stringPreferencesKey("details")

        override suspend fun getCollection(page: Int): ArtCollection? =
            get(collectionKeyOf(page), ArtCollection.serializer())

        override suspend fun setCollection(page: Int, collection: ArtCollection) =
            set(collectionKeyOf(page), ArtCollection.serializer(), collection)

        override suspend fun getDetails(): ArtDetails? =
            get(DETAILS, ArtDetails.serializer())

        override suspend fun setDetails(details: ArtDetails) =
            set(DETAILS, ArtDetails.serializer(), details)


    }