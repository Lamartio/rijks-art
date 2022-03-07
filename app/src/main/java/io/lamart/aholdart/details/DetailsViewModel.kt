package io.lamart.aholdart.details

import androidx.lifecycle.ViewModel
import io.lamart.aholdart.domain.ArtDetails
import io.lamart.aholdart.toStateDelegate
import kotlinx.coroutines.flow.Flow

class DetailsViewModel(
    details: Flow<ArtDetails?>,
    isLoading: Flow<Boolean>,
    val loadDetails: (objectNumber: String) -> Unit,
    val clearDetails: () -> Unit,
) : ViewModel() {

    val details by details.toStateDelegate(null)
    val isLoading by isLoading.toStateDelegate(false)

    override fun onCleared() {
        super.onCleared()
        clearDetails()
    }
}