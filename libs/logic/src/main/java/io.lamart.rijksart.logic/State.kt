package io.lamart.rijksart.logic

import io.lamart.rijksart.domain.ArtCollection
import io.lamart.rijksart.domain.ArtDetails
import io.lamart.rijksart.optics.async.Async
import io.lamart.rijksart.optics.async.initial

data class State(
    val collections: Map<Int, Async<ArtCollection>> = emptyMap(),
    val details: Async<ArtDetails> = initial(),
)