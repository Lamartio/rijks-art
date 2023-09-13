package io.lamart.rijksart.logic

import io.lamart.lux.Async
import io.lamart.lux.Machine
import io.lamart.lux.Mutable
import io.lamart.rijksart.Platform
import io.lamart.rijksart.PlatformDependencies
import io.lamart.rijksart.httpEngineFactory
import io.lamart.rijksart.logic.details.DetailsMachine
import io.lamart.rijksart.logic.details.DetailsState
import io.lamart.rijksart.logic.overview.OverviewMachine
import io.lamart.rijksart.logic.overview.OverviewState
import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow

fun Platform.toMachine(): RijksMachine = RijksMachine(this)

class RijksMachine(platform: Platform) : Machine<RijksState, RijksActions>(initialize(platform)) {
    val overview = this
        .compose(
            actions = RijksActions::overview,
            state = it {
                OverviewState(
                    isFetching = fetchingCollection.state is Async.Executing,
                    selection = this.selection.item?.id,
                    items = collections.items.map(it {
                        OverviewState.Item(id, title, headerImage.url)
                    })
                )
            }
        )
        .let(::OverviewMachine)
    val details = this
        .compose(
            actions = RijksActions::details,
            state = it {
                DetailsState(
                    title = selection.item?.title ?: "",
                    imageUrl = selection.item?.webImage?.url,
                    description = selection.details?.artObjectPage?.plaqueDescription ?: "",
                    isFetching = selection.fetchingDetails.state is Async.Executing
                )
            }
        )
        .let(::DetailsMachine)
}

fun <T, R> it(transform: T.() -> R): (T) -> R = transform

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
