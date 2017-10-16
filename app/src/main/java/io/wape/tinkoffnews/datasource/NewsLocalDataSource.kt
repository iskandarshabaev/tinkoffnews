package io.wape.tinkoffnews.datasource

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import io.wape.tinkoffnews.utils.Resource
import io.wape.tinkoffnews.db.AppDatabase
import io.wape.tinkoffnews.db.entity.NewsEntity

class NewsLocalDataSource(val db: AppDatabase) : NewsDataSource {

    override fun getNews(): LiveData<Resource<List<NewsEntity>>> {
        return Transformations.map(db.newsDao().loadAllNews(), { Resource.success(it) })
    }

    override fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>> {
        return Transformations.map(db.newsDao().loadNewsContent(id), { Resource.success(it) })
    }
}
