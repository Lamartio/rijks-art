package io.lamart.rijksart

import io.lamart.lux.Async
import io.lamart.rijksart.model.ArtCollection
import io.lamart.rijksart.model.ArtDetails

data class RijksState(
    val fetchingCollection: Async<Int, ArtCollection> = Async.Idle(),
    val collections: Map<Int, Async<Int, ArtCollection>> = emptyMap(),
    val details: Async<Int, ArtDetails> = Async.Idle()
)

