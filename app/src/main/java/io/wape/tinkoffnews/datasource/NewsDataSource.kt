package io.wape.tinkoffnews.datasource

import android.arch.lifecycle.LiveData
import io.wape.tinkoffnews.Resource
import io.wape.tinkoffnews.db.entity.NewsEntity

interface NewsDataSource {

    fun getNews(): LiveData<Resource<List<NewsEntity>>>

    fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>>

}
