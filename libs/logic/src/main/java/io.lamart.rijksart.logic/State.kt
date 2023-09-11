package io.lamart.rijksart.logic

import io.lamart.lux.Async
import io.lamart.rijksart.domain.ArtCollection
import io.lamart.rijksart.domain.ArtDetails

data class State(
    val collections: Map<Int, Async<Int, ArtCollection>> = emptyMap(),
    val details: Async<Int, ArtDetails> = Async.Idle(),
)