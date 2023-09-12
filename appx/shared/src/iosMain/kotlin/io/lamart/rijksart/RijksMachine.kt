package io.lamart.rijksart

import com.liftric.kvault.KVault
import io.lamart.lux.Machine

actual class RijksMachine : Machine<RijksState, RijksActions>(RijksState(), { scope, focus ->
    RijksActions(object : RijksDepedencies {
        override val focus = focus
        override val vault = KVault()
        override val scope = scope
    })
})