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

    val art by collection
        .map {
            it
                .result
                ?.artObjects
                ?.groupBy { it.principalOrFirstMaker }
                ?.flatMap { (maker, art) ->
                    listOf(Item.headerOf(maker)) + art.map(Item.Companion::artOf)
                }
                ?: emptyList()
        }
        .toStateDelegate(emptyList<Item>())

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

    companion object {
        fun headerOf(text: String): Item = Header(text, text)
        fun artOf(o: ArtCollection.ArtObject): Item =
            with(o) {
                Art(id, title, longTitle, webImage?.url, objectNumber)
            }
    }
}