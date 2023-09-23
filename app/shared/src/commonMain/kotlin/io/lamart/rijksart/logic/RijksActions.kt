package io.lamart.rijksart.logic

import io.lamart.rijksart.logic.details.DetailsActions
import io.lamart.rijksart.logic.gallery.GalleryActions

class RijksActions internal constructor(deps: RijksDepedencies) {
    internal val gallery = GalleryActions(deps)
    internal val details = DetailsActions(deps)

    /**
     * Action that will initialize the state.
     *
     * IMPORTANT: call this in the initialization of each app.
     */

    fun initialize() = gallery.loadNextPage()
}