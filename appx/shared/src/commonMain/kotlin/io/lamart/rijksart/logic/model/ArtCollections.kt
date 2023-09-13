package io.lamart.rijksart.logic.model

import ArtCollection
import io.lamart.lux.focus.lensOf

class ArtCollections(val value: Map<Int, ArtCollection> = emptyMap()) {

    val items: List<ArtCollection.ArtObject>
        get() = value.entries
            .sortedBy { it.key }
            .flatMap { (_, value) -> value.artObjects }
            .distinctBy { it.id }

    companion object {
        val value = lensOf(ArtCollections::value, { ArtCollections(it) })
    }
}