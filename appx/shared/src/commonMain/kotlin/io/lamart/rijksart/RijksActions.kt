package io.lamart.rijksart

import io.lamart.lux.Behavior
import io.lamart.lux.actions.toAsyncActions
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.model.ArtCollection

class RijksActions internal constructor(deps: RijksDepedencies) : RijksDepedencies by deps {
    private val getCollectionActions = focus
        .compose(lensOf({ fetchingCollection }, { copy(fetchingCollection = it) }))
        .toAsyncActions(
            scope,
            Behavior.exhausting(
                flow = dataFlowOf(
                    get = { page -> vault.string("collection_${page}")?.decode<ArtCollection>() },
                    fetch = { ArtCollection(artObjects = emptyList()) }, // TODO
                    set = ArtCollection::encode
                )
            )
        )

    fun getCollection(page: Int) = getCollectionActions.start(page)
}