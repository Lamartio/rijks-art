package io.lamart.rijksart.logic

import io.lamart.lux.focus.lensOf
import io.lamart.rijksart.logic.details.Selection
import io.lamart.rijksart.logic.overview.Overview

data class RijksState internal constructor(
    internal val overview: Overview = Overview(),
    internal val selection: Selection = Selection()
) {
    companion object {
        internal val overview = lensOf(RijksState::overview, { copy(overview = it) })
        internal val selection = lensOf(RijksState::selection, { copy(selection = it) })
    }
}



