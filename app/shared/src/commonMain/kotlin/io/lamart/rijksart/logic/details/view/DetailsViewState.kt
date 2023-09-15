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

internal fun DetailsState.toDetailsViewState(): DetailsViewState {
    val artObject = fetchingDetails.result?.artObject
    val artPage = fetchingDetails.result?.artObjectPage

    return DetailsViewState(
        title =  artObject?.title ?: selected?.title ?: "",
        imageUrl = artObject?.webImage?.url ?: selected?.webImage?.url,
        description = artPage?.plaqueDescription ?: artObject?.plaqueDescriptionEnglish ?: "",
        isFetching = fetchingDetails.state is Async.Executing,
        isSelected = selected != null,
    )
}