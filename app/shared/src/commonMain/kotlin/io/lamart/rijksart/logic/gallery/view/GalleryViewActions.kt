package io.lamart.rijksart.logic.gallery.view

import io.lamart.rijksart.logic.RijksActions

class GalleryViewActions internal constructor(private val actions: RijksActions) {
    fun select(id: String) = actions.details.select(id)
    fun loadNextPage() = actions.gallery.loadNextPage()
}