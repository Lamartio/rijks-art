package io.lamart.rijksart

import io.lamart.lux.Machine

expect class RijksMachine : Machine<RijksState, RijksActions>

val RijksMachine.details get() = compose(state = { it.details })