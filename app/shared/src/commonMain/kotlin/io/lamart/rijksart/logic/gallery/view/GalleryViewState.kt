package io.lamart.rijksart.logic.gallery.view

import io.lamart.lux.Async
import io.lamart.rijksart.it
import io.lamart.rijksart.logic.RijksState

data class GalleryViewState internal constructor(
    val isFetching: Boolean,
    val selection: String?,
    val items: List<Item>,
) {

    val isShowingDetails: Boolean
        get() = selection != null

    data class Item internal constructor(
        val id: String,
        val title: String,
        val imageUrl: String?
    )
}

internal fun RijksState.toGalleryViewState() =
    GalleryViewState(
        isFetching = gallery.fetchingPage.state is Async.Executing,
        selection = details.selected?.id,
        items = gallery.items.map(it {
            GalleryViewState.Item(id, title, headerImage.url)
        })
    )