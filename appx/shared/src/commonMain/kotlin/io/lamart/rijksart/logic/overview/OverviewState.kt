package io.lamart.rijksart.logic.overview

data class OverviewState internal constructor(
    val isFetching: Boolean,
    val selection: String?,
    val items: List<Item>
) {
    data class Item internal constructor(
        val id: String,
        val title: String,
        val imageUrl: String?
    )
}