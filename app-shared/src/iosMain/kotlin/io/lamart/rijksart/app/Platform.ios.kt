package io.lamart.rijksart.app

import com.liftric.kvault.KVault
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

private fun test(): KVault {
    return KVault()
}
