package io.lamart.rijksart

import com.liftric.kvault.KVault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@OptIn(ExperimentalCoroutinesApi::class)
class Storage(
    private val vault: KVault,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO.limitedParallelism(1),
) {

    @Suppress("UNCHECKED_CAST")
    inline operator fun <reified T : Any> get(key: String): Storage.Value<T> =
        when (T::class) {
            Boolean::class -> Value(key, KVault::bool, KVault::set) as Storage.Value<T>
            Int::class -> Value(key, KVault::int, KVault::set) as Storage.Value<T>
            Float::class -> Value(key, KVault::float, KVault::set) as Storage.Value<T>
            Double::class -> Value(key, KVault::double, KVault::set) as Storage.Value<T>
            String::class -> Value(key, KVault::string, KVault::set) as Storage.Value<T>
            else -> Value(key, KVault::any, KVault::set)
        }

    inner class Value<T : Any>(
        private val key: String,
        private val get: KVault.(String) -> T?,
        private val set: KVault.(String, T) -> Boolean,
    ) {

        suspend fun get(): T? =
            withContext(dispatcher) { this@Value.get(vault, key) }

        suspend fun set(value: T): Boolean =
            withContext(dispatcher) { this@Value.set(vault, key, value) }

    }

}

inline fun <reified T : Any> KVault.any(key: String): T? =
    this
        .string(key)
        ?.runCatching<String, T>(Json::decodeFromString)
        ?.getOrNull()

inline fun <reified T> KVault.set(key: String, value: T): Boolean =
    value
        .runCatching(Json::encodeToString)
        .map { set(key, it) }
        .getOrDefault(false)



