package io.lamart.rijksart.logic.details.view

import io.lamart.lux.Async
import io.lamart.rijksart.logic.details.DetailsState

data class DetailsViewState internal constructor(
    val title: String,
    val imageUrl: String?,
    val description: String,
    val isFetching: Boolean,
    val isSelected: Boolean
)

internal fun DetailsState.toDetailsViewState(): DetailsViewState =
    DetailsViewState(
        title = selected?.title ?: "",
        imageUrl = selected?.webImage?.url,
        description = details?.artObjectPage?.plaqueDescription ?: "",
        isFetching = fetchingDetails.state is Async.Executing,
        isSelected = selected != null,
    )