package io.lamart.rijksart.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data object DetailsView : View {
    @Composable
    override fun invoke() {
        val machine = rijks().machine.details.forView
        val state by machine.collectAsState()

        Scaffold(topBar = { RijksTopBar(state.title, machine.actions::deselect) }) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    model = state.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Divider()
                Text(
                    text = state.description,
                    modifier = Modifier.padding(
                        PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}