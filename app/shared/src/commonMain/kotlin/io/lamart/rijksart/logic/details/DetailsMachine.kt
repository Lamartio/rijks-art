package io.lamart.rijksart.logic.details

import io.lamart.lux.Async
import io.lamart.lux.Machine
import io.lamart.rijksart.logic.RijksActions
import io.lamart.rijksart.logic.RijksMachine
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.details.view.DetailsViewActions
import io.lamart.rijksart.logic.details.view.DetailsViewMachine
import io.lamart.rijksart.logic.details.view.DetailsViewState

class DetailsMachine private constructor(
    machine: Machine<DetailsState, DetailsActions>,
    val viewModel: DetailsViewMachine,
) : Machine<DetailsState, DetailsActions>(machine) {

    companion object {

        internal operator fun invoke(parent: RijksMachine): DetailsMachine {
            val machine = parent
                .compose(state = RijksState::details, actions = RijksActions::details)
            val viewModel = machine
                .compose(state = DetailsState::toViewState, actions = ::DetailsViewActions)
                .let(::DetailsViewMachine)

            return DetailsMachine(machine, viewModel)
        }

    }
}

private fun DetailsState.toViewState(): DetailsViewState {
    val artObject = fetchingDetails.result?.artObject
    val artPage = fetchingDetails.result?.artObjectPage

    return DetailsViewState(
        title = artObject?.title ?: selected?.title ?: "",
        imageUrl = artObject?.webImage?.url ?: selected?.webImage?.url,
        description = artPage?.plaqueDescription ?: artObject?.plaqueDescriptionEnglish ?: "",
        isFetching = fetchingDetails.state is Async.Executing,
        isSelected = selected != null,
    )
}