package io.lamart.rijksart.logic.details.view

import io.lamart.rijksart.logic.details.DetailsActions

class DetailsViewActions internal constructor(private val actions: DetailsActions){
    fun deselect() = actions.select(null)
    fun fetchDetails() = actions.fetchDetails()
}