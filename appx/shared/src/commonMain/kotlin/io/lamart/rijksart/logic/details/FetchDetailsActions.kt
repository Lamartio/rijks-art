package io.lamart.rijksart.logic.details

import io.lamart.lux.Async
import io.lamart.lux.Behavior
import io.lamart.lux.actions.Actions
import io.lamart.lux.actions.toStreamActions
import io.lamart.rijksart.dataFlowOf
import io.lamart.rijksart.get
import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.network.ArtDetails
import kotlinx.coroutines.flow.onEach

internal class FetchDetailsActions(deps: RijksDepedencies) : Actions<String> by deps.run({
    focus
        .compose(RijksState.selection)
        .compose(Selection.fetchingDetails)
        .toStreamActions(
            scope,
            Behavior.switching(flow = dataFlowOf(
                get = { vault.get<ArtDetails>("details_${it}").get() },
                fetch = museum::getDetails,
                set = { objectNumber, item ->
                    vault.get<ArtDetails>("details_${objectNumber}").set(item)
                }
            )),
            effect = { flow ->
                flow.onEach { (state, result) ->
                    if (state is Async.Executing || state is Async.Success) {
                        focus
                            .compose(RijksState.selection)
                            .compose(Selection.details)
                            .set(result)
                    }
                }
            }
        )
})