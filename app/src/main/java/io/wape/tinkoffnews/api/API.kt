package io.wape.tinkoffnews.api

import io.wape.tinkoffnews.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Объект с инстансом интерфейса NewsInterface
 * Создает инстанс если он еще не создан
 */
object API {

    private var sNews: NewsInterface? = null

    fun news(): NewsInterface {
        var instance = sNews
        if (instance == null) {
            synchronized(API::class.java) {
                instance = sNews
                if (instance == null) {
                    sNews = create(NewsInterface::class.java)
                    instance = sNews
                }
            }
        }
        return instance!!
    }

    private fun <T> create(tClass: Class<T>): T {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(OkHttpProvider.provideClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(tClass)
    }
}
