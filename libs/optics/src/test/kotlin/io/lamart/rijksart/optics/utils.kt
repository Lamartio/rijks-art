package io.lamart.rijksart.optics

import io.lamart.rijksart.optics.async.Async
import io.lamart.rijksart.optics.async.initial

data class State(val downloading: Async<String> = initial())
