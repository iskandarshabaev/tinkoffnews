package io.wape.tinkoffnews.datasource

import android.arch.lifecycle.LiveData
import io.wape.tinkoffnews.utils.Resource
import io.wape.tinkoffnews.db.entity.NewsEntity

/**
 * Базовый интерфейс источника данных для новостей
 */
interface NewsDataSource {

    /**
     * Возвращает список всех новостей
     *
     * @return список всех новостей
     */
    fun getNews(): LiveData<Resource<List<NewsEntity>>>

    /**
     * Возвращает новость по id
     */
    fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>>

}
