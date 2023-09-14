package io.lamart.rijksart.logic.details

import io.lamart.lux.Machine
import io.lamart.rijksart.logic.RijksActions
import io.lamart.rijksart.logic.RijksMachine
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.details.view.DetailsViewMachine
import io.lamart.rijksart.logic.details.view.toDetailsViewState

class DetailsMachine internal constructor(parent: RijksMachine) :
    Machine<DetailsState, DetailsActions>(parent.child) {

    val forView = this
        .compose(state = DetailsState::toDetailsViewState)
        .let(::DetailsViewMachine)

}

private val RijksMachine.child: Machine<DetailsState, DetailsActions>
    get() = compose(
        state = RijksState::details,
        actions = RijksActions::details
    )