package io.lamart.rijksart.logic

import ArtCollection
import io.lamart.lux.Behavior
import io.lamart.lux.Stream
import io.lamart.lux.actions.Actions
import io.lamart.lux.actions.toStreamActions
import io.lamart.rijksart.dataFlowOf
import io.lamart.rijksart.get
import io.lamart.rijksart.logic.model.ArtCollections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold

internal class FetchCollectionsActions(deps: RijksDepedencies) : Actions<Int> by deps.run({
    focus
        .compose(RijksState.fetchingCollection)
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
                                .compose(RijksState.collections)
                                .compose(ArtCollections.value)
                                .modify { collections ->
                                    collections.plus(page to collection)
                                }
                        }
                    }
            }
        )
})

private fun <T> Flow<T>.record(initial: T): Flow<Pair<T, T>> =
    this
        .map { it to it }
        .runningFold(initial to initial) { (_, old), (new) -> old to new }
        .drop(1)