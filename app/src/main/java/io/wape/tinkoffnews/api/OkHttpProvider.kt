package io.wape.tinkoffnews.api

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient

import io.wape.tinkoffnews.api.ApiProtocol.V_1

class OkHttpProvider {
    companion object {

        @Volatile private var sClientV2: OkHttpClient? = null

        fun provideClient(): OkHttpClient {
            var client = sClientV2
            if (client == null) {
                synchronized(OkHttpClient::class.java) {
                    client = sClientV2
                    if (client == null) {
                        sClientV2 = buildClientV2()
                        client = sClientV2
                    }
                }
            }
            return client!!
        }

        private fun buildClientV2(): OkHttpClient {
            val builder = makeBaseBuilder()
            return builder.build()
        }

        private fun makeBaseBuilder(): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
            return builder.connectTimeout(10, TimeUnit.SECONDS)
        }
    }
}
