package io.lamart.aholdart.logic

import io.lamart.aholdart.optics.compose
import io.lamart.aholdart.optics.lensOf

interface Actions {
    /**
     * Will get the latest results from persistence and loads it into the state
     */
    fun initialize()

    val getAndFetchCollection: (payload: CollectionPayload) -> Unit
    val getAndFetchDetails: (objectNumber: String) -> Unit
}

internal fun Dependencies.toActions(): Actions =
    object : Actions {

        override val getAndFetchCollection: (CollectionPayload) -> Unit =
            source
                .compose(lensOf({ collection }, { copy(collection = it) }))
                .toAsyncAction(
                    strategy = latest(getAndFetch(
                        { storage.getCollection() },
                        storage::setCollection,
                        { (page, pageSize) -> museum.getCollection(page, pageSize) })
                    ),
                    scope
                )

        override val getAndFetchDetails: (String) -> Unit =
            source
                .compose(lensOf({ details }, { copy(details = it) }))
                .toAsyncAction(
                    strategy = latest(getAndFetch(
                        { storage.getDetails() },
                        storage::setDetails,
                        museum::getDetails
                    )),
                    scope
                )

        override fun initialize() {
            getAndFetchCollection(CollectionPayload())
        }

    }
