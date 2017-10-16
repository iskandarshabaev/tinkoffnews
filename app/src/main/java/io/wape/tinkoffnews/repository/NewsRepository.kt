package io.wape.tinkoffnews.repository

import android.arch.lifecycle.LiveData
import io.wape.tinkoffnews.utils.Resource
import io.wape.tinkoffnews.db.entity.NewsEntity

/**
 * Репозиторий для получения данных по новостям
 */
interface NewsRepository {

    /**
     * Возвращает список всех новостей
     */
    fun getNews(): LiveData<Resource<List<NewsEntity>>>

    /**
     * Возвращает новость по id
     */
    fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>>
}
