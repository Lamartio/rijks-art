package io.lamart.rijksart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.lamart.rijksart.details.DetailsViewModel
import io.lamart.rijksart.logic.Logic
import io.lamart.rijksart.main.MainViewModel
import io.lamart.rijksart.main.getCollection
import io.lamart.rijksart.main.isLoadingCollection
import io.lamart.rijksart.optics.async.Async
import kotlinx.coroutines.flow.map


class RijksViewModelProviderFactory(
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
