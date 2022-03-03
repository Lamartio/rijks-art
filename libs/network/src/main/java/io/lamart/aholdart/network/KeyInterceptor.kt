package io.lamart.aholdart.network

import okhttp3.Interceptor
import okhttp3.Response


internal class KeyInterceptor(private val key: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain
            .request()
            .newBuilder()
            .url(chain.appendParameterToUrl("key", key))
            .build()
            .let(chain::proceed)

}

internal fun Interceptor.Chain.appendParameterToUrl(key: String, value: String) =
    this
        .request()
        .url
        .newBuilder()
        .addQueryParameter(key, value)
        .build()
