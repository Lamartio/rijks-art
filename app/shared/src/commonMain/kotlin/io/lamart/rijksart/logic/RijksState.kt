package io.lamart.rijksart.logic

import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.logic.details.DetailsState
import io.lamart.rijksart.logic.gallery.GalleryState

data class RijksState internal constructor(
    internal val gallery: GalleryState = GalleryState(),
    internal val details: DetailsState = DetailsState()
) {
    companion object {
        internal val gallery = lensOf(RijksState::gallery, { copy(gallery = it) })
        internal val selection = lensOf(RijksState::details, { copy(details = it) })
    }
}



