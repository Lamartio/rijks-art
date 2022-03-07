package io.lamart.aholdart.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.android.material.composethemeadapter.MdcTheme

@Composable
fun MainContent(viewModel: MainViewModel) =
    with(viewModel) {
        MdcTheme {
            LazyColumn {
                val items = collection.value

                items(items.size, { items[it].key }) { index ->
                    val item = items[index]

                    when (item) {
                        is Item.Header -> HeaderItem(item.text)
                        is Item.Art -> ArtItem(item) { }
                        is Item.More -> MoreButton(loadMore)
                    }
                }
            }

            Loader(isLoading = isLoading.value)
        }
    }

@Composable
fun MoreButton(loadMore: () -> Unit) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(onClick = { loadMore() }) {
            Text(text = "Load more...")
        }
    }
}

@Composable
fun Loader(isLoading: Boolean) =
    AnimatedVisibility(visible = isLoading) {
        Row(modifier = Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.Center) {
            val modifier = Modifier
                .wrapContentWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(Int.MAX_VALUE.dp)
                )

            Card(modifier) {
                CircularProgressIndicator(modifier = Modifier
                    .size(36.dp)
                    .padding(6.dp))
            }
        }
    }

@Composable
fun HeaderItem(text: String) =
    Column {
        Text(
            text = AnnotatedString(text),
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider()
    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtItem(item: Item.Art, clicks: (item: Item.Art) -> Unit) =
    with(item) {
        Column(Modifier.clickable { clicks(item) }) {
            ListItem(
                icon = {
                    AsyncImage(
                        model = icon,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(40.dp),
                    )
                },
                text = {
                    Text(
                        text,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                secondaryText = {
                    Text(
                        description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
            Divider(startIndent = 72.dp)
        }
    }