package io.lamart.rijksart.logic.overview

import io.lamart.rijksart.network.model.ArtCollection
import arrow.optics.Getter
import io.lamart.lux.Stream
import io.lamart.lux.focus.lensOf

internal data class Overview(
    internal val pages: Map<Int, ArtCollection> = emptyMap(),
    internal val fetchingPage: Stream<Int, ArtCollection> = Stream(),
)  {

    internal val items: List<ArtCollection.ArtObject>
        get() = pages.entries
            .sortedBy { it.key }
            .flatMap { (_, value) -> value.artObjects }
            .distinctBy { it.id }

    companion object {
        internal val pages = lensOf(Overview::pages, { copy(pages = it) })
        internal val fetchingPage =
            lensOf(Overview::fetchingPage, { copy(fetchingPage = it) })

        internal val items = Getter(Overview::items)
    }
}