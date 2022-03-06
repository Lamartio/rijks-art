package io.lamart.aholdart.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view
            .findViewById<ComposeView>(R.id.content)
            .setContent {
                MdcTheme { // or AppCompatTheme
                    Greeting(viewModel)
                    Loading(viewModel)
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
