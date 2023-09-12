package io.lamart.rijksart

import com.liftric.kvault.KVault
import io.lamart.lux.focus.FocusedLens
import kotlinx.coroutines.CoroutineScope

internal interface RijksDepedencies {
    val focus: FocusedLens<*, RijksState>
    val vault: KVault
    val scope: CoroutineScope
}