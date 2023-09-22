package io.lamart.rijksart.logic.details

import io.lamart.lux.Behavior
import io.lamart.lux.actions.Actions
import io.lamart.lux.actions.toStreamActions
import io.lamart.rijksart.dataFlowOf
import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.network.model.ArtDetails

internal class FetchDetailsActions(deps: RijksDepedencies) : Actions<String> by deps.run({
    focus
        .compose(RijksState.details)
        .compose(DetailsState.fetchingDetails)
        .toStreamActions(
            scope,
            Behavior.switching(flow = dataFlowOf(
                get = { storage.get<ArtDetails>("details_${it}").get() },
                fetch = museum::getDetails,
                set = { objectNumber, item ->
                    storage
                        .get<ArtDetails>("details_${objectNumber}")
                        .set(item)
                }
            ))
        )
})