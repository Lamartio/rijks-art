package io.lamart.aholdart.logic

import io.lamart.aholdart.domain.ArtCollection
import io.lamart.aholdart.domain.ArtDetails
import io.lamart.aholdart.logic.async.Async

data class State(
    val collection: Async<CollectionPayload, ArtCollection> = Async.Success(
        payload = CollectionPayload(),
        result = ArtCollection(emptyList())
    ),
    val details: ArtDetails? = null,
)

data class CollectionPayload(val page: Int = 0, val pageSize: Int = 10)