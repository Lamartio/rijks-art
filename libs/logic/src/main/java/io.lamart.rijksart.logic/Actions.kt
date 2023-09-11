package io.lamart.rijksart.logic

import io.lamart.lux.Async
import io.lamart.lux.Behavior.Companion.switching
import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.logic.utils.getAndFetch

interface Actions {
    fun appendCollection()
    fun getAndFetchCollection(page: Int)
    fun resetDetails()
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
                behavior = switching(getAndFetch(
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
                        get = { objectNumber -> storage.getDetails(objectNumber) },
                        set = { objectNumber, value -> storage.setDetails(objectNumber, value) },
                        fetch = museum::getDetails
                    )),
                    scope
                )

        override fun resetDetails() {
            val details = source.lens.compose(lensOf({ details }, { copy(details = it) }))

            if (details.get() !is Async.Executing) {
                details.set(initial())
            }
        }

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