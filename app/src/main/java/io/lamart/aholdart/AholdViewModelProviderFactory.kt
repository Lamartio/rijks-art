package io.lamart.aholdart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.lamart.aholdart.details.DetailsViewModel
import io.lamart.aholdart.logic.Logic
import io.lamart.aholdart.main.MainViewModel
import io.lamart.aholdart.main.getCollection
import io.lamart.aholdart.main.isLoadingCollection
import io.lamart.aholdart.optics.async.Async
import kotlinx.coroutines.flow.map


class AholdViewModelProviderFactory(
    private val defaultFactory: ViewModelProvider.Factory,
    private val getLogic: () -> Logic,
) : ViewModelProvider.Factory {

    private val logic: Logic
        get() = getLogic()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            MainViewModel::class.java -> MainViewModel(
                collection = logic.state.getCollection(),
                isLoading = logic.state.isLoadingCollection(),
                loadMore = logic.actions::appendCollection,
                loadDetails = logic.actions.getAndFetchDetails
            )
            DetailsViewModel::class.java -> DetailsViewModel(
                details = logic.state.map { it.details.result },
                isLoading = logic.state.map { it.details is Async.Executing },
                loadDetails = logic.actions.getAndFetchDetails,
                clearDetails = logic.actions::resetDetails
            )
            else -> defaultFactory.create(modelClass)
        } as T
}
