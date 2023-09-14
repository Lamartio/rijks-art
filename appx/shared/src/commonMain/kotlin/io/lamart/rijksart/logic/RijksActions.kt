package io.lamart.rijksart.logic

import io.lamart.rijksart.logic.details.DetailsActions
import io.lamart.rijksart.logic.gallery.GalleryActions

class RijksActions internal constructor(deps: RijksDepedencies) {
    internal val details = DetailsActions(deps)
    internal val gallery = GalleryActions(deps, details::select)

    fun initialize() = gallery.loadNextPage()
}