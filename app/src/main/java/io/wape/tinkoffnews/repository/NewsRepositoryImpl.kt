package io.wape.tinkoffnews.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import io.wape.tinkoffnews.utils.AppExecutors
import io.wape.tinkoffnews.Resource
import io.wape.tinkoffnews.Status
import io.wape.tinkoffnews.datasource.NewsLocalDataSource
import io.wape.tinkoffnews.datasource.NewsNetworkDataSource
import io.wape.tinkoffnews.db.entity.NewsEntity


class NewsRepositoryImpl(
        private val appExecutors: AppExecutors,
        private val localDataSource: NewsLocalDataSource,
        private val networkDataSource: NewsNetworkDataSource
) : NewsRepository {

    override fun getNews(needsUpdate: Boolean): LiveData<Resource<List<NewsEntity>>> {
        var result = MediatorLiveData<Resource<List<NewsEntity>>>()
        val apiResponse = networkDataSource.getNews()
        result.addSource(apiResponse, { r ->
            if (r != null) {
                when (r.status) {
                    Status.SUCCESS -> {
                        appExecutors.diskIO.execute {
                            localDataSource.db.newsDao().insertAll(r.data!!)
                            appExecutors.mainThread.execute {
                                result.addSource(localDataSource.db.newsDao().loadAllNews(), {
                                    result.value = Resource.success(it)
                                })
                            }
                        }
                    }
                    Status.ERROR -> {
                        appExecutors.diskIO.execute {
                            result.addSource(localDataSource.db.newsDao().loadAllNews(), {
                                result.value = Resource.error(r.throwable!!, it)
                            })
                        }
                    }
                }
            }
        })
        return result
    }

    override fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>> {
        var result = MediatorLiveData<Resource<NewsEntity>>()
        val apiResponse = networkDataSource.getNewsContent(id)
        result.addSource(apiResponse, { r ->
            if (r != null) {
                when (r.status) {
                    Status.SUCCESS -> {
                        appExecutors.diskIO.execute {
                            localDataSource.db.newsDao().insert(r.data!!)
                            appExecutors.mainThread.execute {
                                result.addSource(localDataSource.db.newsDao().loadNewsContent(id), {
                                    result.value = Resource.success(it)
                                })
                            }
                        }
                    }
                    Status.ERROR -> {
                        appExecutors.diskIO.execute {
                            result.addSource(localDataSource.db.newsDao().loadNewsContent(id), {
                                result.value = Resource.error(r.throwable!!, it)
                            })
                        }
                    }
                }
            }
        })
        return result
    }
}
