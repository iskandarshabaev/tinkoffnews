package io.wape.tinkoffnews.repository

import android.arch.lifecycle.LiveData
import io.wape.tinkoffnews.Resource
import io.wape.tinkoffnews.db.entity.NewsEntity

interface NewsRepository {

    fun getNews(needsUpdate: Boolean): LiveData<Resource<List<NewsEntity>>>

    fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>>
}
