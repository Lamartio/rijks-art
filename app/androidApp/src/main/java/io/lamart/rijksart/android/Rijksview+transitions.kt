package io.lamart.rijksart.android

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val duration = 400

val enterRight: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    { slideIntoContainer(towards = Right, animationSpec = tween(duration)) }

val enterLeft: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    { slideIntoContainer(towards = Left, animationSpec = tween(duration)) }

val exitRight: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    { slideOutOfContainer(towards = Right, animationSpec = tween(duration)) }

val exitLeft: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    { slideOutOfContainer(towards = Left, animationSpec = tween(duration)) }