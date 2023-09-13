package io.lamart.rijksart.logic.overview

import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState

class OverviewActions internal constructor(
    private val deps: RijksDepedencies,
    private val selectDetail: (id: String?) -> Unit
) {
    private val fetchCollectionActions = FetchCollectionsActions(deps)

    fun select(id: String?) =
        selectDetail(id)

    fun loadNextPage() =
        deps.focus
            .compose(RijksState.collections)
            .get()
            .keys
            .maxOrNull()
            .let { it ?: 0 }
            .plus(1)
            .let(fetchCollectionActions::start)
}