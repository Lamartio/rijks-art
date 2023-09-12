package io.lamart.rijksart

import android.content.Context
import com.liftric.kvault.KVault
import io.lamart.lux.Machine

actual class RijksMachine(context: Context) :
    Machine<RijksState, RijksActions>(RijksState(), { scope, focus ->
        RijksActions(object : RijksDepedencies {
            override val focus = focus
            override val vault = KVault(context)
            override val scope = scope
        })
    })