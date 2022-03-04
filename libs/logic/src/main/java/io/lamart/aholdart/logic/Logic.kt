package io.lamart.aholdart.logic

import io.lamart.aholdart.rijksmuseum.RijksMuseum
import io.lamart.aholdart.services.Services
import kotlinx.coroutines.flow.Flow

interface Logic {

    val state: Flow<State>

    val actions: Actions

}


fun logicOf(museum: RijksMuseum, services: Services) {


}


