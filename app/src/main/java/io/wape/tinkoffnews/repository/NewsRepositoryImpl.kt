package io.wape.tinkoffnews.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import io.wape.tinkoffnews.datasource.NewsLocalDataSource
import io.wape.tinkoffnews.datasource.NewsNetworkDataSource
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.utils.AppExecutors
import io.wape.tinkoffnews.utils.Resource
import io.wape.tinkoffnews.utils.Status

/**
 * Реализация репозитория новостей
 */
class NewsRepositoryImpl(
        private val appExecutors: AppExecutors,
        private val localDataSource: NewsLocalDataSource,
        private val networkDataSource: NewsNetworkDataSource
) : NewsRepository {

    /**
     * Возвращает список всех новостей
     * Сначала делается запрос на сервер, при удачном выполнении запроса новости сохраняются в базу и
     * подгружаются из базы в отсортированном виде.
     * Если запрос на сервер не удался, то возвращаются закешированные данные из базы(если они есть)
     */
    override fun getNews(): LiveData<Resource<List<NewsEntity>>> {
        val result = MediatorLiveData<Resource<List<NewsEntity>>>()
        val apiResponse = networkDataSource.getNews()
        result.addSource(apiResponse, { r ->
            if (r != null) {
                when (r.status) {
                    Status.SUCCESS -> {
                        runOnDisk {
                            localDataSource.db.newsDao().insertAll(r.data!!)
                            runOnMain {
                                result.addSource(localDataSource.db.newsDao().loadAllNews(), {
                                    result.value = Resource.success(it)
                                })
                            }
                        }
                    }
                    Status.ERROR -> {
                        runOnDisk {
                            result.addSource(localDataSource.db.newsDao().loadAllNews(),
                                    { showError(result, r.throwable!!, it) })
                        }
                    }
                    else -> {
                    }
                }
            }
        })
        return result
    }

    /**
     * Возвращает новость по id
     * Сначала делается запрос на сервер, при удачном выполнении запроса новость сохраняется в базу и
     * подгружается из базы в обновленном виде.
     * Если запрос на сервер не удался, то возвращаются закешированные данные из базы(если они есть)
     * В случае если такой новости нет в базе вернется Resource<NULL>
     */
    override fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>> {
        val result = MediatorLiveData<Resource<NewsEntity>>()
        runOnDisk {
            val localResult = localDataSource.db.newsDao().loadNewsContent(id)
            runOnMain {
                result.addSource(localResult, { local ->
                    if (local == null || local.content.isNullOrEmpty()) {
                        result.removeSource(localResult)
                        val apiResponse = networkDataSource.getNewsContent(id)
                        result.addSource(apiResponse, { r ->
                            if (r != null) {
                                when (r.status) {
                                    Status.SUCCESS -> {
                                        runOnDisk {
                                            localDataSource.db.newsDao().insert(r.data!!)
                                            runOnMain {
                                                result.addSource(localResult, {
                                                    result.value = Resource.success(it)
                                                })
                                            }
                                        }
                                    }
                                    Status.ERROR -> {
                                        runOnDisk {
                                            result.addSource(localResult, { showError(result, r.throwable!!, it) })
                                        }
                                    }
                                    else -> {
                                    }
                                }
                            }
                        })
                    } else {
                        result.value = Resource.success(local)
                    }
                })
            }
        }
        return result
    }

    /**
     * Вспомогательный метод для выполнения метода в дисковом потоке
     * @param f метод, который необходимо выполнить
     */
    private fun runOnDisk(f: () -> Unit) {
        appExecutors.diskIO.execute(f)
    }

    /**
     * Вспомогательный метод для выполнения метода в главном потоке
     * @param f метод, который необходимо выполнить
     */
    private fun runOnMain(f: () -> Unit) {
        appExecutors.mainThread.execute(f)
    }

    /**
     * Вспомогательный метод для вывода ошибки
     */
    private fun <T> showError(result: MediatorLiveData<Resource<T>>, throwable: Throwable, data: T?) {
        runOnMain {
            result.value = Resource.error(throwable, data)
        }
    }
}
