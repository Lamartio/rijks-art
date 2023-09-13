package io.lamart.rijksart.logic

import ArtCollection
import arrow.optics.Getter
import arrow.optics.Setter
import io.lamart.lux.Behavior
import io.lamart.lux.Stream
import io.lamart.lux.actions.toStreamActions
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.dataFlowOf
import io.lamart.rijksart.get
import io.lamart.rijksart.logic.model.ArtCollections
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold

//flow = dataFlowOf(
//get = { page -> entryOf(page).get() },
//fetch = museum::getCollection,
//set = { page, value -> entryOf(page).set(value) }
//)

class RijksActions internal constructor(private val deps: RijksDepedencies) {
    private val getCollectionActions = with(deps) {
        focus
            .compose(RijksState.fetchingCollection)
            .toStreamActions(
                scope,
                Behavior.switching(flow = dataFlowOf(
                    get = { vault.get<ArtCollection>("collection_${it}").get() },
                    fetch = { museum.getCollection(it) },
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
    }

    fun getCollection(page: Int) = getCollectionActions.start(page)


    /**
     * Generic action that needs to be called at the 'beginning' of each app.
     */

    fun initialize() = appendCollection()

    fun appendCollection() =
        deps.focus
            .compose(Getter(RijksState::collections))
            .compose(ArtCollections.value)
            .get()
            .keys
            .maxOrNull()
            .let { it ?: 0 }
            .plus(1)
            .let(::getCollection)

    fun select(id: String?) =
        deps.focus
            .compose(RijksState.selection)
            .set(id)

    fun cancel() = deps.scope.cancel()
}

//
//private fun RijksDepedencies.entryOf(page: Int): Entry<ArtCollection> =
//    vault["collection_${page}"]


private fun <T> Flow<T>.record(initial: T): Flow<Pair<T, T>> =
    this
        .map { it to it }
        .runningFold(initial to initial) { (_, old), (new) -> old to new }
        .drop(1)