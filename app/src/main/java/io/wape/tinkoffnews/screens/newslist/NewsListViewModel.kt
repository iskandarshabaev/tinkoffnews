package io.wape.tinkoffnews.screens.newslist

import android.arch.lifecycle.*
import io.wape.tinkoffnews.Resource
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.repository.NewsRepository

class NewsListViewModel(repository: NewsRepository) : ViewModel() {

    private val reloadDataTrigger = MutableLiveData<Boolean>()
    val news: LiveData<Resource<List<NewsEntity>>>

    init {
        news = Transformations.switchMap(reloadDataTrigger) { repository.getNews(true) }
    }

    fun reload() {
        reloadDataTrigger.value = true
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsListViewModel(repository) as T
        }
    }

}