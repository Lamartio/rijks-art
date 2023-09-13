package io.lamart.rijksart.logic.overview

import io.lamart.rijksart.logic.FetchCollectionsActions
import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.model.ArtCollections

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
            .compose(ArtCollections.value)
            .get()
            .keys
            .maxOrNull()
            .let { it ?: 0 }
            .plus(1)
            .let(fetchCollectionActions::start)
}