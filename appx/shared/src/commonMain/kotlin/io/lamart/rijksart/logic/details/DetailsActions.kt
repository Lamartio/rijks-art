package io.lamart.rijksart.logic.details

import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.overview.Overview
import io.lamart.rijksart.transaction

class DetailsActions internal constructor(private val deps: RijksDepedencies) {
    private val fetchDetailsActions = FetchDetailsActions(deps)

    fun select(id: String?) {
        fetchDetailsActions.stop()

        deps.focus.modify(transaction { focus ->
            val selected = focus
                .compose(RijksState.overview)
                .compose(Overview.items)
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

    fun deselect() = select(null)

    fun fetchDetails() {
        deps.focus
            .compose(RijksState.selection)
            .compose(Selection.selected)
            .get()
            ?.objectNumber
            ?.let(fetchDetailsActions::start)
    }
}