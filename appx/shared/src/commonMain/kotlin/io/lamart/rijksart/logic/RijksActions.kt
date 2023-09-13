package io.lamart.rijksart.logic

import arrow.optics.Getter
import io.lamart.rijksart.logic.details.DetailsActions
import io.lamart.rijksart.logic.model.ArtCollections
import io.lamart.rijksart.logic.overview.OverviewActions
import kotlinx.coroutines.cancel

class RijksActions internal constructor(private val deps: RijksDepedencies) {
    val details = DetailsActions(deps)
    val overview = OverviewActions(deps, details::select)

    fun initialize() = overview.loadNextPage()
}