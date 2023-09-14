package io.lamart.rijksart.logic.details.view

import io.lamart.lux.Machine
import io.lamart.rijksart.logic.details.DetailsActions

class DetailsViewMachine internal constructor(machine: Machine<DetailsViewState, DetailsViewActions>) :
    Machine<DetailsViewState, DetailsViewActions>(machine)