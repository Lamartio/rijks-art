package io.lamart.rijksart.logic.overview

import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState

class OverviewActions internal constructor(
    deps: RijksDepedencies,
    val select: (id: String?) -> Unit
): RijksDepedencies by deps {
    private val fetchCollectionActions = FetchCollectionActions(deps)

    fun loadNextPage() =
        focus
            .compose(RijksState.overview)
            .compose(Overview.pages)
            .get()
            .keys
            .maxOrNull()
            .let { it ?: 0 }
            .plus(1)
            .let(fetchCollectionActions::start)
}