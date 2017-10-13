package io.wape.tinkoffnews.screens.newsdetails

import android.arch.lifecycle.*
import io.wape.tinkoffnews.Resource
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.repository.NewsRepository

class NewsContentViewModel(repository: NewsRepository) : ViewModel() {

    private val idTrigger = MutableLiveData<Long>().apply { value = 0 }
    val newsContent: LiveData<Resource<NewsEntity>>

    init {
        newsContent = Transformations.switchMap(idTrigger) { repository.getNewsContent(it) }
    }

    fun load(id: Long) {
        if (idTrigger.value == id) {
            return
        }
        idTrigger.value = id
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsContentViewModel(repository) as T
        }
    }

}