package io.lamart.aholdart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.lamart.aholdart.logic.Logic
import io.lamart.aholdart.main.MainViewModel
import io.lamart.aholdart.main.getCollection
import io.lamart.aholdart.main.isLoadingCollection


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
                initialize = { logic.actions.appendCollection() }
            )
            MainViewModel::class.java -> MainViewModel(
                collection = logic.state.getCollection(),
                isLoading = logic.state.isLoadingCollection(),
                loadMore = logic.actions::appendCollection
            )
            else -> defaultFactory.create(modelClass)
        } as T
}
