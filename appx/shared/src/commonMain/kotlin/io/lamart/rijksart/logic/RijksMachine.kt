package io.lamart.rijksart.logic

import io.lamart.lux.Machine
import io.lamart.lux.Mutable
import io.lamart.rijksart.Platform
import io.lamart.rijksart.PlatformDependencies
import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow

fun Platform.toMachine(): RijksMachine = RijksMachine(this)

class RijksMachine(platform: Platform) :
    Machine<RijksState, RijksActions>(initialize(platform))

private fun initialize(platform: PlatformDependencies): Machine<RijksState, RijksActions> {
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
