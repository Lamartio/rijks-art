package io.lamart.rijksart.logic

import io.lamart.rijksart.logic.details.DetailsActions
import io.lamart.rijksart.logic.overview.OverviewActions

class RijksActions internal constructor(deps: RijksDepedencies) {
    val details = DetailsActions(deps)
    val overview = OverviewActions(deps, details::select)

    fun initialize() = overview.loadNextPage()
}