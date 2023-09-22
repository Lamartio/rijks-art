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

fun Platform.toMachine(): RijksMachine = RijksMachine(this)


/**
 * The root of the logic: This class contains all the logic for the application, though represent it segregated.
 */

class RijksMachine internal constructor(platform: Platform) :
    Machine<RijksState, RijksActions>(initialize(platform)) {
    val gallery = GalleryMachine(this)
    val details = DetailsMachine(this)
}

private fun initialize(platform: Platform): Machine<RijksState, RijksActions> {
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    val state = MutableStateFlow(RijksState())
    val mutable = Mutable(get = state::value, set = { state.value = it })
    val deps = object : RijksDepedencies, PlatformDependencies by platform {
        override val focus = mutable.lens
        override val scope = scope
        override val museum = RijksMuseum()
    }
    val actions = RijksActions(deps)

    return Machine(scope, state, actions)
}
