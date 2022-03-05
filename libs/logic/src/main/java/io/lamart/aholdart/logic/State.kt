package io.lamart.aholdart.logic

import io.lamart.aholdart.domain.ArtCollection
import io.lamart.aholdart.domain.ArtDetails
import io.lamart.aholdart.optics.async.Async
import io.lamart.aholdart.optics.async.initial

data class State(
    val collection: Async<ArtCollection> = initial(),
    val details: Async<ArtDetails> = initial(),
)

data class CollectionPayload(val page: Int = 0, val pageSize: Int = 10)