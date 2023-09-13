package io.lamart.rijksart.logic

import io.lamart.lux.Machine
import io.lamart.lux.Mutable
import io.lamart.rijksart.Platform
import io.lamart.rijksart.PlatformDependencies
import io.lamart.rijksart.httpEngineFactory
import io.lamart.rijksart.logic.details.DetailsMachine
import io.lamart.rijksart.logic.details.toDetailsState
import io.lamart.rijksart.logic.overview.OverviewMachine
import io.lamart.rijksart.logic.overview.toOverviewState
import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow

fun Platform.toMachine(): RijksMachine = RijksMachine(this)

class RijksMachine(platform: Platform) : Machine<RijksState, RijksActions>(initialize(platform)) {
    val overview = this
        .compose(
            state = RijksState::toOverviewState,
            actions = RijksActions::overview
        )
        .let(::OverviewMachine)
    val details = this
        .compose(
            state = { it.selection.toDetailsState() },
            actions = RijksActions::details
        )
        .let(::DetailsMachine)
}

private fun initialize(platform: PlatformDependencies): Machine<RijksState, RijksActions> {
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    val state = MutableStateFlow(RijksState())
    val mutable = Mutable(get = state::value, set = { state.value = it })
    val deps = object : RijksDepedencies, PlatformDependencies by platform {
        override val focus = mutable.lens
        override val scope = scope
        override val museum = RijksMuseum(platform.httpEngineFactory)
    }
    val actions = RijksActions(deps)

    return Machine(scope, state, actions)
}
