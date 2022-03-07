package io.lamart.aholdart.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.composethemeadapter.MdcTheme
import io.lamart.aholdart.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        inflater.inflate(R.layout.main_fragment, container, false)

    @OptIn(ExperimentalFoundationApi::class,
        androidx.compose.material.ExperimentalMaterialApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view
            .let { it as ComposeView }
            .setContent {
                MdcTheme {
                    LazyColumn {
                        // Add a single item

                        stickyHeader {
                            Text(text = "Header")
                        }

                        val items = viewModel.art.value

                        // Add 5 items
                        items(items.size, { items[it].key }) { index ->
                            val item = items[index]

                            when (item) {
                                is Item.Header -> with(this@LazyColumn) {
                                    stickyHeader(item.key) {
                                        Text(text = item.text)
                                    }
                                }
                                is Item.Art -> ListItem(
                                    icon = {
                                        Icon(
                                            Icons.Filled.Favorite,
                                            contentDescription = null,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    },
                                    text = { Text(item.text) },
                                    secondaryText = { Text(item.description) },
                                    singleLineSecondaryText = true
                                )
                            }
                        }
                    }
                }
            }
    }
}

@Composable
private fun Greeting(model: MainViewModel) {
    Text(
        text = model.title.value ?: "",
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun Loading(model: MainViewModel) {
    Text(
        text = model.isLoading.value.toString(),
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
