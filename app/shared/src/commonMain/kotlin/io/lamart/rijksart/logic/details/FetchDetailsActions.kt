package io.lamart.rijksart.logic.details

import io.lamart.lux.Behavior
import io.lamart.lux.actions.Actions
import io.lamart.lux.actions.toStreamActions
import io.lamart.rijksart.Storage
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
            Behavior.switching(
                flow = dataFlowOf(
                    get = storage::load,
                    fetch = museum::getDetails,
                    set = storage::save
                )
            )
        )
})

private suspend fun Storage.load(objectNumber: String): ArtDetails? =
    get<ArtDetails>("details_${objectNumber}").get()

private suspend fun Storage.save(objectNumber: String, details: ArtDetails): Boolean =
    get<ArtDetails>("details_${objectNumber}").set(details)