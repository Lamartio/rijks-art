package io.lamart.rijksart.logic.details.view

import io.lamart.lux.Async
import io.lamart.rijksart.logic.details.DetailsState

data class DetailsViewState(
    val title: String = "",
    val imageUrl: String? = null,
    val description: String = "",
    val isFetching: Boolean = false,
    val isSelected: Boolean= true
)

internal fun DetailsState.toDetailsViewState(): DetailsViewState =
    DetailsViewState(
        title = selected?.title ?: "",
        imageUrl = selected?.webImage?.url,
        description = details?.artObjectPage?.plaqueDescription ?: "",
        isFetching = fetchingDetails.state is Async.Executing,
        isSelected = selected != null,
    )