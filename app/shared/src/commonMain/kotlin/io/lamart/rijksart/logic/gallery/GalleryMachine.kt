package io.lamart.rijksart.logic.gallery

import io.lamart.lux.Async
import io.lamart.lux.Machine
import io.lamart.rijksart.it
import io.lamart.rijksart.logic.RijksActions
import io.lamart.rijksart.logic.RijksMachine
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.gallery.view.GalleryViewActions
import io.lamart.rijksart.logic.gallery.view.GalleryViewMachine
import io.lamart.rijksart.logic.gallery.view.GalleryViewState

class GalleryMachine private constructor(
    machine: Machine<GalleryState, GalleryActions>,
    val viewModel: GalleryViewMachine,
) : Machine<GalleryState, GalleryActions>(machine) {

    companion object {

        internal operator fun invoke(parent: RijksMachine): GalleryMachine {
            val gallery = parent
                .compose(state = RijksState::gallery, actions = RijksActions::gallery)
            val viewModel = parent
                .compose(state = RijksState::toViewState, actions = ::GalleryViewActions)
                .let(::GalleryViewMachine)

            return GalleryMachine(gallery, viewModel)
        }

    }
}

private fun RijksState.toViewState() =
    GalleryViewState(
        isFetching = gallery.fetchingPage.state is Async.Executing,
        selection = details.selected?.id,
        items = gallery.items.map(it {
            GalleryViewState.Item(id, title, headerImage.url)
        })
    )