package io.lamart.aholdart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.lamart.aholdart.logic.Logic
import io.lamart.aholdart.main.MainViewModel
import kotlinx.coroutines.flow.map

class AholdViewModelProviderFactory(
    private val defaultFactory: ViewModelProvider.Factory,
    private val getLogic: () -> Logic,
) : ViewModelProvider.Factory {

    val logic: Logic
        get() = getLogic()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            AholdViewModel::class.java -> AholdViewModel(
                initialize = { logic.actions.getAndFetchCollection(0) }
            )
            MainViewModel::class.java -> MainViewModel(logic.state.map { it.collection })
            else -> defaultFactory.create(modelClass)
        } as T
}