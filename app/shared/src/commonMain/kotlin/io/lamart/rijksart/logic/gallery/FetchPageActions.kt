package io.lamart.rijksart.logic.gallery

import io.lamart.rijksart.network.model.ArtCollection
import io.lamart.lux.Behavior
import io.lamart.lux.Stream
import io.lamart.lux.actions.Actions
import io.lamart.lux.actions.toStreamActions
import io.lamart.rijksart.dataFlowOf
import io.lamart.rijksart.get
import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.record
import kotlinx.coroutines.flow.onEach

internal class FetchPageActions(deps: RijksDepedencies) : Actions<Int> by deps.run({
    focus
        .compose(RijksState.gallery)
        .compose(GalleryState.fetchingPage)
        .toStreamActions(
            scope,
            Behavior.exhausting(flow = dataFlowOf(
                get = { vault.get<ArtCollection>("collection_${it}").get() },
                fetch = museum::getCollection,
                set = { page, item ->
                    vault.get<ArtCollection>("collection_${page}").set(item)
                }
            )),
            effect = { flow ->
                flow
                    .record(Stream())
                    .onEach { (old, new) ->
                        val page = old.state.asExecuting()?.input
                        val collection = new.result

                        if (page != null && collection != null) {
                            focus
                                .compose(RijksState.gallery)
                                .compose(GalleryState.pages)
                                .modify { it + (page to collection) }
                        }
                    }
            }
        )
})
