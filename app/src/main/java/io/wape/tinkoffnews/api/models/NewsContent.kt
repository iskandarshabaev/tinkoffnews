package io.wape.tinkoffnews.api.models

import com.google.gson.annotations.SerializedName
import io.wape.tinkoffnews.db.entity.NewsEntity

/**
 * Структура для хранения новости и ее контента
 */
class NewsContent {
    @SerializedName("title")
    private var title: NewsEntity = NewsEntity()
    @SerializedName("content")
    private var content: String = ""

    /**
     * Создает NewsEntity с заполненным контентом
     */
    fun newsEntity(): NewsEntity {
        title.content = content
        return title
    }
}
