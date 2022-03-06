package io.lamart.aholdart.main

import androidx.lifecycle.ViewModel
import io.lamart.aholdart.domain.ArtCollection
import io.lamart.aholdart.optics.async.Async
import io.lamart.aholdart.toStateDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(collection: Flow<Async<ArtCollection>>) : ViewModel() {

    val title by collection
        .map { it.result?.artObjects?.size?.toString() }
        .toStateDelegate("")
    val isLoading by collection
        .map { it is Async.Executing }
        .toStateDelegate(false)

}