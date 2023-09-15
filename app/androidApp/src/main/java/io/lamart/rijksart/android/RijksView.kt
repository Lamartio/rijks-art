package io.lamart.rijksart.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RijksView() {
    val navController: NavHostController = rememberNavController()

    CompositionLocalProvider(LocalNavHostControllerProvider provides navController) {
        NavHost(
            navController = navController,
            startDestination = GalleryView.tag
        ) {
            composable(route = GalleryView.tag, content = { GalleryView() })
            composable(route = DetailsView.tag, content = { DetailsView() })
        }
    }
}