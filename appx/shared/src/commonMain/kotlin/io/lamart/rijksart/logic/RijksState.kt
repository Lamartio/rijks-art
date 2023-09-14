package io.lamart.rijksart.logic

import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.logic.details.DetailsState
import io.lamart.rijksart.logic.overview.Overview

data class RijksState internal constructor(
    internal val overview: Overview = Overview(),
    internal val details: DetailsState = DetailsState()
) {
    companion object {
        internal val overview = lensOf(RijksState::overview, { copy(overview = it) })
        internal val selection = lensOf(RijksState::details, { copy(details = it) })
    }
}



