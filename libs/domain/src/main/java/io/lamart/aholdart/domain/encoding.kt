package io.lamart.aholdart.domain

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

fun <T> SerializationStrategy<T>.encode(value: T): String =
    Json.encodeToString(this, value)

fun <T> DeserializationStrategy<T>.decode(string: String): T =
    Json.decodeFromString(this, string)