package io.lamart.rijksart.logic.details

import ArtCollection
import io.lamart.lux.Async
import io.lamart.lux.Stream
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.network.ArtDetails

internal data class Selection(
    val selected: ArtCollection.ArtObject? = null,
    val details: ArtDetails? = null,
    val fetchingDetails: Stream<String, ArtDetails> = Stream(),
) {
    companion object {
        internal val selected = lensOf(Selection::selected, { copy(selected = it) })
        internal val details = lensOf(Selection::details, { copy(details = it) })
        internal val fetchingDetails =
            lensOf(Selection::fetchingDetails, { copy(fetchingDetails = it) })
    }
}

internal fun Selection.toDetailsState(): DetailsState =
    DetailsState(
        title = selected?.title ?: "",
        imageUrl = selected?.webImage?.url,
        description = details?.artObjectPage?.plaqueDescription ?: "",
        isFetching = fetchingDetails.state is Async.Executing,
        isSelected = selected != null,
    )