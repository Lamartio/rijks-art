package io.lamart.rijksart.logic

import io.lamart.lux.Machine
import io.lamart.lux.Mutable
import io.lamart.rijksart.Platform
import io.lamart.rijksart.PlatformDependencies
import io.lamart.rijksart.logic.details.DetailsMachine
import io.lamart.rijksart.logic.gallery.GalleryMachine
import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * The root of the logic: This class contains all the logic for the application, though represents it segregated.
 */

class RijksMachine internal constructor(machine: Machine<RijksState, RijksActions>) :
    Machine<RijksState, RijksActions>(machine) { // this is a gimmicky way of constructing, but it is necessary for iOS. In iOS it is not handy to have09 kotlin classes with generics.

    val gallery = GalleryMachine(this)
    val details = DetailsMachine(this)

}

fun Platform.toMachine(): RijksMachine {
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    val state = MutableStateFlow(RijksState())
    val mutable = Mutable(get = state::value, set = { state.value = it })
    val deps = object : RijksDepedencies, PlatformDependencies by this {
        override val focus = mutable.lens
        override val scope = scope
        override val museum = RijksMuseum()
    }
    val actions = RijksActions(deps)
    val machine = Machine(scope, state, actions)

    return RijksMachine(machine)
}
