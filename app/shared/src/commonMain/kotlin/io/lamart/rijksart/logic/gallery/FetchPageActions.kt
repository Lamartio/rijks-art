package io.lamart.rijksart.logic.gallery

import io.lamart.lux.Behavior
import io.lamart.lux.Stream
import io.lamart.lux.actions.Actions
import io.lamart.lux.actions.toStreamActions
import io.lamart.rijksart.Storage
import io.lamart.rijksart.dataFlowOf
import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.network.model.ArtCollection
import io.lamart.rijksart.record
import kotlinx.coroutines.flow.onEach


internal class FetchPageActions(deps: RijksDepedencies) : Actions<Int> by deps.run({
    focus
        .compose(RijksState.gallery)
        .compose(GalleryState.fetchingPage)
        .toStreamActions(
            scope,
            Behavior.exhausting(
                flow = dataFlowOf(
                    get = storage::load,
                    fetch = museum::getCollection,
                    set = storage::save
                )
            ),
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

private suspend fun Storage.load(page: Int): ArtCollection? =
    get<ArtCollection>("collection_${page}").get()

private suspend fun Storage.save(page: Int, details: ArtCollection): Boolean =
    get<ArtCollection>("collection_${page}").set(details)