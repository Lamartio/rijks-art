package io.lamart.aholdart.logic

import io.lamart.aholdart.domain.ArtCollection
import io.lamart.aholdart.domain.ArtDetails
import io.lamart.aholdart.optics.async.Async
import io.lamart.aholdart.optics.async.initial

data class State(
    val collections: Map<Int, Async<ArtCollection>> = emptyMap(),
    val details: Async<ArtDetails> = initial(),
)