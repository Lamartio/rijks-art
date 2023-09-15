package io.lamart.rijksart.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlin.reflect.KProperty


@Composable
fun rijks() :RijksApplication =
    LocalContext.current.applicationContext.let { it as RijksApplication }

@Composable
operator fun <T> CompositionLocal<T>.getValue(thisRef: Nothing?, prop: KProperty<*>): T =
    current

val LocalNavHostControllerProvider = staticCompositionLocalOf<NavHostController> {
    TODO("No ${NavHostController::class.java.name} has been provided.")
}