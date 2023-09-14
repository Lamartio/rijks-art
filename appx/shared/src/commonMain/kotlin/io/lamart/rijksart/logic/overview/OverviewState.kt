package io.lamart.rijksart.logic.overview

import io.lamart.lux.Async
import io.lamart.rijksart.it
import io.lamart.rijksart.logic.RijksState

data class OverviewState internal constructor(
    val isFetching: Boolean,
    val selection: String?,
    val items: List<Item>
) {

    val isShowingDetails: Boolean
        get() = selection != null

    data class Item internal constructor(
        val id: String,
        val title: String,
        val imageUrl: String?
    )
}

internal fun RijksState.toOverviewState() =
    OverviewState(
        isFetching = overview.fetchingPage.state is Async.Executing,
        selection = details.selected?.id,
        items = overview.items.map(it {
            OverviewState.Item(id, title, headerImage.url)
        })
    )