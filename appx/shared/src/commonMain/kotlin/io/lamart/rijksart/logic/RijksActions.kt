package io.lamart.rijksart.logic

import io.lamart.rijksart.logic.details.DetailsActions
import io.lamart.rijksart.logic.overview.OverviewActions

class RijksActions internal constructor(deps: RijksDepedencies) {
    internal val details = DetailsActions(deps)
    internal val overview = OverviewActions(deps, details::select)

    fun initialize() = overview.loadNextPage()
}