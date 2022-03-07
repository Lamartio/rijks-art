package io.lamart.rijksart.main

import androidx.lifecycle.ViewModel
import io.lamart.rijksart.domain.ArtCollection
import io.lamart.rijksart.logic.State
import io.lamart.rijksart.optics.async.Async
import io.lamart.rijksart.toStateDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(
    collection: Flow<List<Item>>,
    isLoading: Flow<Boolean>,
    val loadMore: () -> Unit,
    val loadDetails: (objectNumber: String) -> Unit,
) : ViewModel() {

    val isLoading by isLoading.toStateDelegate(false)

    val collection by collection.toStateDelegate(emptyList<Item>())

}

sealed class Item(open val key: String) {
    data class Header(override val key: String, val text: String) : Item(key)
    data class Art(
        override val key: String,
        val text: String,
        val description: String,
        val icon: String?,
        val objectNumber: String,
    ) : Item(key)

    object More : Item("more_button_123")

    companion object {
        fun headerOf(text: String): Item = Header(text, text)
        fun artOf(o: ArtCollection.ArtObject): Item =
            with(o) {
                Art(id, title, longTitle, webImage?.url, objectNumber)
            }
    }
}

fun Flow<State>.isLoadingCollection(): Flow<Boolean> =
    map { state ->
        state
            .collections
            .any { (_, value) -> value is Async.Executing }
    }

fun Flow<State>.getCollection(): Flow<List<Item>> =
    map { state ->
        state
            .collections
            .toSortedMap()
            .values
            .flatMap { async ->
                when (async) {
                    is Async.Success<ArtCollection> -> async.result.artObjects
                    else -> emptyList()
                }
            }
            .groupBy { it.principalOrFirstMaker }
            .flatMap { (maker, art) ->
                listOf(Item.headerOf(maker)) + art.map(Item.Companion::artOf)
            }
            .plus(Item.More)
    }