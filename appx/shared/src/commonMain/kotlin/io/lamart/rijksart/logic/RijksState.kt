package io.lamart.rijksart.logic

import ArtCollection
import io.lamart.lux.Stream
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.logic.model.ArtCollections
import io.lamart.rijksart.network.ArtDetails

data class RijksState internal constructor(
    val fetchingCollection: Stream<Int, ArtCollection> = Stream(),
    val collections: ArtCollections = ArtCollections(),
    val selection: Selection = Selection()
) {

    val isShowingDetails: Boolean
        get() = selection.item != null

    companion object {
        internal val fetchingCollection =
            lensOf(RijksState::fetchingCollection, { copy(fetchingCollection = it) })
        internal val collections = lensOf(RijksState::collections, { copy(collections = it) })
        internal val selection = lensOf(RijksState::selection, { copy(selection = it) })
    }
}

data class Selection(
    val item: ArtCollection.ArtObject? = null,
    val details: ArtDetails? = null,
    val fetchingDetails: Stream<String, ArtDetails> = Stream(),
) {
    companion object {
        val item = lensOf(Selection::item, { copy(item = it) })
        val details = lensOf(Selection::details, { copy(details = it) })
        val fetchingDetails = lensOf(Selection::fetchingDetails, { copy(fetchingDetails = it) })
    }
}


