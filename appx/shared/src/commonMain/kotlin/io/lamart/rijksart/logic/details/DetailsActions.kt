package io.lamart.rijksart.logic.details

import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.Selection
import io.lamart.rijksart.transaction

class DetailsActions internal constructor(private val deps: RijksDepedencies) {
    private val fetchDetailsActions = FetchDetailsActions(deps)

    fun select(id: String?) {
        fetchDetailsActions.stop()
        deps.focus.modify(transaction { focus ->
            val item = focus
                .compose(RijksState.collections)
                .get()
                .items
                .firstOrNull { it.id == id }

            focus
                .compose(RijksState.selection)
                .modify { selection ->
                    if (item == null || item != selection.item)
                        selection.copy(item = item, details = null)
                    else
                        selection
                }
        })
        fetchDetails()
    }

    fun deselect() = select(null)

    fun fetchDetails() {
        deps.focus
            .compose(RijksState.selection)
            .compose(Selection.item)
            .get()
            ?.objectNumber
            ?.let(fetchDetailsActions::start)
    }
}