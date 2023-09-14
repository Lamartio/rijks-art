package io.lamart.rijksart.logic.details

import io.lamart.lux.Machine
import io.lamart.rijksart.logic.details.view.DetailsViewMachine
import io.lamart.rijksart.logic.details.view.toDetailsViewState

class DetailsMachine internal constructor(machine: Machine<DetailsState, DetailsActions>) :
    Machine<DetailsState, DetailsActions>(machine) {

    val forView = this
        .compose(state = DetailsState::toDetailsViewState)
        .let(::DetailsViewMachine)
}