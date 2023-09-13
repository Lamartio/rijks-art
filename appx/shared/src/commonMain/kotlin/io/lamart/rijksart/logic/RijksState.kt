package io.lamart.rijksart.logic

import ArtCollection
import io.lamart.lux.Stream
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.logic.model.ArtCollections

data class RijksState(
    val fetchingCollection: Stream<Int, ArtCollection> = Stream(),
    val collections: ArtCollections = ArtCollections(),
    val selection: String? = null
//    val details: Async<Int, ArtDetails> = Async.Idle()
) {
    companion object {
        val fetchingCollection =
            lensOf(RijksState::fetchingCollection, { copy(fetchingCollection = it) })
        val collections = lensOf(RijksState::collections, { copy(collections = it) })
        val selection = lensOf(RijksState::selection, { copy(selection = it) })
    }
}


val Map<Int, ArtCollection>.artObjects: List<ArtCollection.ArtObject>
    get() = entries
        .sortedBy { it.key }
        .flatMap { (_, value) -> value.artObjects }
        .distinctBy { it.id }



