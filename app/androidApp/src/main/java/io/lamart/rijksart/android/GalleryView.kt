package io.lamart.rijksart.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.lamart.rijksart.logic.gallery.view.GalleryViewState

data object GalleryView : View {

    @Composable
    override operator fun invoke() {
        val machine = rijks().machine.gallery.forView
        val state by machine.collectAsState()
        val (host, stack) = navigation()

        if (state.isShowingDetails && stack == listOf(tag))
            host.navigate(DetailsView.tag)

        Scaffold(topBar = { RijksTopBar() }) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(state.items, key = GalleryViewState.Item::id) { item ->
                    ItemView(item, machine.actions::select)
                }

                item(key = "_load_more_button_") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.padding(8.dp),
                            onClick = machine.actions::loadNextPage,
                            content = { Text(text = "load more") },
                            enabled = !state.isFetching
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemView(item: GalleryViewState.Item, select: (String) -> Unit) {
    Box(
        modifier = Modifier.clickable { select(item.id) },
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            modifier = Modifier
                .height(88.dp)
                .fillMaxWidth()
                .alpha(.2f),
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = item.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}