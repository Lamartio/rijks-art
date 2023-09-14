package io.lamart.rijksart.android

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController

sealed interface View {
    val tag: String
        get() = this::class.java.name

    @Composable
    fun navigation(): Pair<NavHostController, List<String>> {
        val hostController = LocalNavHostControllerProvider.current
        val stack by hostController.currentBackStack.collectAsState()
        val tags = stack.map { it.destination.route }.let { tags ->
            val index = tags.indexOf(tag)

            tags
                .filterIndexed { i, _ -> i >= index }
                .filterNotNull()
        }

        return hostController to tags
    }

    @Composable
    operator fun invoke() = Text(text = tag)
}