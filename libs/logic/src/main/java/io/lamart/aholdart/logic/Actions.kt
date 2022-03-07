package io.lamart.aholdart.logic

import io.lamart.aholdart.logic.utils.getAndFetch
import io.lamart.aholdart.optics.async.Async
import io.lamart.aholdart.optics.async.initial
import io.lamart.aholdart.optics.async.latest
import io.lamart.aholdart.optics.async.toAsyncAction
import io.lamart.aholdart.optics.compose
import io.lamart.aholdart.optics.lensOf

interface Actions {
    fun appendCollection()
    fun getAndFetchCollection(page: Int)
    val getAndFetchDetails: (objectNumber: String) -> Unit
}

internal fun Dependencies.toActions(): Actions =
    object : Actions {


        override fun getAndFetchCollection(page: Int) {
            val collectionSource = source
                .compose(lensOf({ collections }, { copy(collections = it) }))
                .compose(lensOf(
                    select = { get(page) ?: initial() },
                    copy = { collection -> plus(page to collection) }
                ))
            val action = collectionSource.toAsyncAction(
                strategy = latest(getAndFetch(
                    get = storage::getCollection,
                    set = { page, collection -> storage.setCollection(page, collection) },
                    fetch = museum::getCollection
                )),
                scope
            )

            action(page)
        }

        override val getAndFetchDetails: (objectNumber: String) -> Unit =
            source
                .compose(lensOf({ details }, { copy(details = it) }))
                .toAsyncAction(
                    strategy = latest(getAndFetch(
                        get = { storage.getDetails() },
                        set = { _, value -> storage.setDetails(value) },
                        fetch = museum::getDetails
                    )),
                    scope
                )

        override fun appendCollection() {
            val collections = source
                .compose(lensOf({ collections }, { copy(collections = it) }))
                .get()
            val isNotExecuting = collections.values.none { it is Async.Executing }

            if (isNotExecuting) {
                val lastPage = collections
                    .filterValues { it is Async.Success }
                    .keys
                    .maxOrNull()
                    ?: -1

                getAndFetchCollection(lastPage + 1)
            }
        }
    }