package io.wape.tinkoffnews.api

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Провайдер http клиента
 * Создает инстанс клиента, если он еще не создан
 */
class OkHttpProvider {
    companion object {

        @Volatile private var sClientV2: OkHttpClient? = null

        fun provideClient(): OkHttpClient {
            var client = sClientV2
            if (client == null) {
                synchronized(OkHttpClient::class.java) {
                    client = sClientV2
                    if (client == null) {
                        sClientV2 = buildClient()
                        client = sClientV2
                    }
                }
            }
            return client!!
        }

        private fun buildClient(): OkHttpClient {
            val builder = makeBaseBuilder()
            return builder.build()
        }

        private fun makeBaseBuilder(): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
            return builder.connectTimeout(10, TimeUnit.SECONDS)
        }
    }
}
