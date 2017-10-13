package io.wape.tinkoffnews.repository

import android.content.Context
import io.wape.tinkoffnews.utils.appExecutorsDefault
import io.wape.tinkoffnews.datasource.DataSourceProvider

object RepositoryProvider {

    @Volatile private var newsRepository: NewsRepository? = null

    fun provideNews(context: Context): NewsRepository {
        var instance = newsRepository
        if (instance == null) {
            synchronized(RepositoryProvider.javaClass) {
                instance = newsRepository
                if (instance == null) {
                    instance = NewsRepositoryImpl(
                            appExecutorsDefault,
                            DataSourceProvider.newsLocal(context),
                            DataSourceProvider.newsNetwork())
                    newsRepository = instance
                }
            }
        }
        return instance!!
    }

}
