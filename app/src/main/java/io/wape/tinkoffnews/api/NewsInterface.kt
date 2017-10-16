package io.wape.tinkoffnews.api

import io.wape.tinkoffnews.api.models.NewsContent
import io.wape.tinkoffnews.api.models.ApiResponse
import io.wape.tinkoffnews.db.entity.NewsEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс для работы с API новостей
 */
interface NewsInterface {

    /**
     * Получение всех новостей
     */
    @GET("news")
    fun loadAllNews(): Call<ApiResponse<List<NewsEntity>>>

    /**
     * Получение контента конкретной новости по id
     */
    @GET("news_content")
    fun loadNewsContent(@Query("id") id: Long): Call<ApiResponse<NewsContent>>

}
