package io.lamart.rijksart.logic.gallery

import io.lamart.rijksart.network.model.ArtCollection
import arrow.optics.Getter
import io.lamart.lux.Stream
import io.lamart.lux.focus.lensOf

data class GalleryState(
    internal val pages: Map<Int, ArtCollection> = emptyMap(),
    internal val fetchingPage: Stream<Int, ArtCollection> = Stream(),
) {

    internal val items: List<ArtCollection.ArtObject>
        get() = pages.entries
            .sortedBy { it.key }
            .flatMap { (_, value) -> value.artObjects }
            .distinctBy { it.id }

    companion object {
        internal val pages = lensOf(GalleryState::pages, { copy(pages = it) })
        internal val fetchingPage =
            lensOf(GalleryState::fetchingPage, { copy(fetchingPage = it) })

        internal val items = Getter(GalleryState::items)
    }
}