package io.wape.tinkoffnews.api.models

import com.google.gson.annotations.SerializedName
import io.wape.tinkoffnews.db.entity.NewsEntity

class NewsContent {
    @SerializedName("title")
    private var title: NewsEntity = NewsEntity()
    @SerializedName("content")
    private var content: String = ""

    fun newsEntity(): NewsEntity {
        title.content = content
        return title
    }
}
