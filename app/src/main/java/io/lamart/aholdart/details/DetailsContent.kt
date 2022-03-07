package io.lamart.aholdart.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DetailsContent(viewModel: DetailsViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Header(
                        viewModel.details.value?.artObject?.labelText ?: "",
                        this@BoxWithConstraints.maxWidth
                    )
                    Title(viewModel.details.value?.artObject?.labelText ?: "")
                    Property(
                        label = "Test",
                        value = viewModel.details.value?.artObject?.labelText ?: ""
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    model: String?,
    width: Dp,
    ratio: Float = 9f / 16,
) {
    AsyncImage(
        model = model,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .height(width * ratio)
            .fillMaxWidth()
    )
}

@Composable
private fun Title(
    text: String,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Property(label: String, value: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption,
        )
        Text(
            text = value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.body1
        )
    }
}