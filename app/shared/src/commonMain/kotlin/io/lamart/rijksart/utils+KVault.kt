package io.lamart.rijksart

import com.liftric.kvault.KVault
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * I needed a simple place to cache network responses in a type-safe manner.
 *
 * TODO: This is not explicitly threaded, which would like to improve through `Dispatchers.IO.limitedParallelism(1)`
 */

@Suppress("UNCHECKED_CAST")
internal inline operator fun <reified T : Any> KVault.get(key: String): Entry<T> =
    when (T::class) {
        Boolean::class -> factory(key, KVault::bool, KVault::set) as Entry<T>
        Int::class -> factory(key, KVault::int, KVault::set) as Entry<T>
        Float::class -> factory(key, KVault::float, KVault::set) as Entry<T>
        Double::class -> factory(key, KVault::double, KVault::set) as Entry<T>
        String::class -> factory(key, KVault::string, KVault::set) as Entry<T>
        else -> factory(key, KVault::any, KVault::set)
    }

private fun <T : Any> KVault.factory(
    key: String,
    get: KVault.(key: String) -> T?,
    set: KVault.(key: String, value: T?) -> Boolean
): Entry<T> =
    Entry(
        get = { get(this, key) },
        set = { value -> if (value != null) set(this, key, value) else deleteObject(key) },
        exists = { existsObject(key) },
        delete = { deleteObject(key) }
    )

class Entry<T : Any> internal constructor(
    val get: () -> T?,
    val set: (value: T?) -> Unit,
    val exists: () -> Boolean,
    val delete: () -> Boolean
) : ReadWriteProperty<Nothing?, T?> {
    override fun getValue(thisRef: Nothing?, property: KProperty<*>): T? = get()
    override fun setValue(thisRef: Nothing?, property: KProperty<*>, value: T?) = set(value)
}

internal inline fun <reified T : Any> KVault.any(key: String): T? =
    this
        .string(key)
        ?.runCatching<String, T>(Json::decodeFromString)
        ?.getOrNull()

internal inline fun <reified T> KVault.set(key: String, value: T): Boolean =
    value
        .runCatching(Json::encodeToString)
        .map { set(key, it) }
        .getOrDefault(false)
