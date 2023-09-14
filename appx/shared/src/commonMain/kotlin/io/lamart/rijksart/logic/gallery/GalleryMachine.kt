package io.lamart.rijksart.logic.gallery

import io.lamart.lux.Machine
import io.lamart.rijksart.logic.RijksActions
import io.lamart.rijksart.logic.RijksMachine
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.logic.gallery.view.GalleryViewActions
import io.lamart.rijksart.logic.gallery.view.GalleryViewMachine
import io.lamart.rijksart.logic.gallery.view.toGalleryViewState

class GalleryMachine internal constructor(parent: RijksMachine) :
    Machine<GalleryState, GalleryActions>(parent.child) {

    val forView = parent
        .compose(state = RijksState::toGalleryViewState, actions = ::GalleryViewActions)
        .let(::GalleryViewMachine)

}

private val RijksMachine.child: Machine<GalleryState, GalleryActions>
    get() = compose(
        state = RijksState::gallery,
        actions = RijksActions::gallery
    )
