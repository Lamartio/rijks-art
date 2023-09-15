package io.lamart.rijksart.logic.details

import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.gallery.GalleryState
import io.lamart.rijksart.transaction

class DetailsActions internal constructor(deps: RijksDepedencies): RijksDepedencies by deps {
    private val fetchDetailsActions = FetchDetailsActions(deps)

    fun select(id: String?) {
        fetchDetailsActions.stop()

        focus.modify(transaction { focus ->
            val selected = focus
                .compose(RijksState.gallery)
                .compose(GalleryState.items)
                .get()
                .firstOrNull { it.id == id }

            focus
                .compose(RijksState.selection)
                .modify { selection ->
                    if (selected == null || selected != selection.selected)
                        selection.copy(selected = selected, details = null)
                    else
                        selection
                }
        })

        fetchDetails()
    }

    fun fetchDetails() {
        focus
            .compose(RijksState.selection)
            .compose(DetailsState.selected)
            .get()
            ?.objectNumber
            ?.let(fetchDetailsActions::start)
    }
}