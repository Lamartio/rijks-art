package io.lamart.rijksart.android

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RijksTopBar(title: String = "Rijksmuseum", pop: (() -> Unit)? = null) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            pop?.let { pop ->
                IconButton(onClick = {
                    pop()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description",
                    )
                }
            }
        }
    )
}