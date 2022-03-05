package io.lamart.aholdart.optics

import io.lamart.aholdart.optics.async.Async
import io.lamart.aholdart.optics.async.initial

data class State(val downloading: Async<String> = initial())
