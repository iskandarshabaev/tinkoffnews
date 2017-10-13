package io.wape.tinkoffnews.api

import io.wape.tinkoffnews.api.models.NewsContent
import io.wape.tinkoffnews.api.models.Resp
import io.wape.tinkoffnews.db.entity.NewsEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("news")
    fun loadAllNews(): Call<Resp<List<NewsEntity>>>

    @GET("news_content")
    fun loadNewsContent(@Query("id") id: Long): Call<Resp<NewsContent>>

}
