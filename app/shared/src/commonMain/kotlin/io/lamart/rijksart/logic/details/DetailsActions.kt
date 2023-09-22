package io.lamart.rijksart.logic.details

import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.gallery.GalleryState
import io.lamart.rijksart.transaction

class DetailsActions internal constructor(deps: RijksDepedencies) : RijksDepedencies by deps {
    private val fetchDetailsActions = FetchDetailsActions(deps)

    /**
     * Selecting the next subject for details requires fetching current details to stop.
     *
     * Next it tries to find the item in the gallery that corresponds to the id. And set that as the new selected painting for details.
     *
     * After selecting, the new painting's details get fetched.
     */

    fun select(id: String?) {
        fetchDetailsActions.stop()

        focus.modify(transaction { focus ->
            val selected = focus
                .compose(RijksState.gallery)
                .compose(GalleryState.items)
                .get()
                .firstOrNull { it.id == id }

            focus
                .compose(RijksState.details)
                .compose(DetailsState.selected)
                .set(selected)
        })

        fetchDetails()
    }

    fun fetchDetails() {
        focus
            .compose(RijksState.details)
            .compose(DetailsState.selected)
            .get()
            ?.objectNumber
            ?.let(fetchDetailsActions::start)
    }
}