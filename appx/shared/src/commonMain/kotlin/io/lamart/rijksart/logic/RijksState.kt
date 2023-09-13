package io.lamart.rijksart.logic

import ArtCollection
import arrow.optics.Getter
import io.lamart.lux.Stream
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.network.ArtDetails

data class RijksState internal constructor(
    internal val fetchingCollection: Stream<Int, ArtCollection> = Stream(),
    internal val collections: Map<Int, ArtCollection> = emptyMap(),
    internal val selection: Selection = Selection()
) {

    internal val collectionItems: List<ArtCollection.ArtObject>
        get() = collections.entries
            .sortedBy { it.key }
            .flatMap { (_, value) -> value.artObjects }
            .distinctBy { it.id }

    val isShowingDetails: Boolean
        get() = selection.selected != null

    internal data class Selection(
        val selected: ArtCollection.ArtObject? = null,
        val details: ArtDetails? = null,
        val fetchingDetails: Stream<String, ArtDetails> = Stream(),
    ) {
        companion object {
            internal val item = lensOf(Selection::selected, { copy(selected = it) })
            internal val details = lensOf(Selection::details, { copy(details = it) })
            internal val fetchingDetails =
                lensOf(Selection::fetchingDetails, { copy(fetchingDetails = it) })
        }
    }

    companion object {
        internal val fetchingCollection =
            lensOf(RijksState::fetchingCollection, { copy(fetchingCollection = it) })
        internal val collections = lensOf(RijksState::collections, { copy(collections = it) })
        internal val collectionItems = Getter(RijksState::collectionItems)
        internal val selection = lensOf(RijksState::selection, { copy(selection = it) })
    }
}



