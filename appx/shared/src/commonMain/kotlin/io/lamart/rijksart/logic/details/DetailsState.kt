package io.lamart.rijksart.logic.details

import ArtCollection
import io.lamart.lux.Async
import io.lamart.lux.Stream
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.network.ArtDetails

data class DetailsState internal constructor(
    val selected: ArtCollection.ArtObject? = null,
    val details: ArtDetails? = null,
    val fetchingDetails: Stream<String, ArtDetails> = Stream(),
) {
    companion object {
        internal val selected = lensOf(DetailsState::selected, { copy(selected = it) })
        internal val details = lensOf(DetailsState::details, { copy(details = it) })
        internal val fetchingDetails =
            lensOf(DetailsState::fetchingDetails, { copy(fetchingDetails = it) })
    }
}
