package io.lamart.rijksart.logic.gallery

import io.lamart.rijksart.logic.RijksDepedencies
import io.lamart.rijksart.logic.RijksState

class GalleryActions internal constructor(deps: RijksDepedencies): RijksDepedencies by deps {
    private val fetchPage = FetchPageActions(deps)

    fun loadNextPage() =
        focus
            .compose(RijksState.gallery)
            .compose(GalleryState.pages)
            .get()
            .keys
            .maxOrNull()
            .let { it ?: 0 }
            .plus(1)
            .let(fetchPage::start)
}