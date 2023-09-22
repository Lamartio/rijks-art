package io.lamart.rijksart.android

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
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
            composable(
                route = GalleryView.tag, content = { GalleryView() },
                enterTransition = enterLeft,
                exitTransition = exitLeft,
                popEnterTransition = enterRight,
                popExitTransition = exitRight
            )
            composable(
                route = DetailsView.tag, content = { DetailsView() },
                enterTransition = enterLeft,
                exitTransition = exitLeft,
                popEnterTransition = enterRight,
                popExitTransition = exitRight
            )
        }
    }
}


private const val duration = 400

val enterRight: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
            animationSpec = tween(duration)
        )
    }
val enterLeft: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
            animationSpec = tween(duration)
        )
    }

val exitRight: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
            animationSpec = tween(duration)
        )
    }

val exitLeft: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
            animationSpec = tween(duration)
        )
    }