package io.lamart.rijksart.logic.details

import io.lamart.lux.Machine

class DetailsMachine internal constructor(machine: Machine<DetailsState, DetailsActions>) : Machine<DetailsState, DetailsActions>(machine)

