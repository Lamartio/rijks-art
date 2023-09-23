package io.lamart.rijksart.logic.details.view

data class DetailsViewState(
    val title: String = "",
    val imageUrl: String? = null,
    val description: String = "",
    val isFetching: Boolean = false,
    val isSelected: Boolean = true,
)