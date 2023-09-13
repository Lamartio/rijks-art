package io.lamart.rijksart.logic.details

data class DetailsState internal constructor(
    val title: String,
    val imageUrl: String?,
    val description: String,
    val isFetching: Boolean,
    val isSelected: Boolean
)