package io.wape.tinkoffnews.screens.newslist

import android.arch.lifecycle.*
import io.wape.tinkoffnews.utils.Resource
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.repository.NewsRepository

/**
 * ViewModel для фрагмента NewsListFragment
 * @param repository репозиторий для работы с новостями
 * Передавать например так: NewsContentViewModel(repositoryProvider.provideNews(yourContext))
 */
class NewsListViewModel(repository: NewsRepository) : ViewModel() {

    /**
     * Триггер для загрузки данных
     */
    private val reloadDataTrigger = MutableLiveData<Boolean>()

    /**
     * LiveData со списком новостей
     */
    val news: LiveData<Resource<List<NewsEntity>>>

    /**
     * Создается news
     * После того как значение reloadDataTrigger.value будет изменено,
     * сработает триггер и вызовется метод repository.getNews()
     */
    init {
        news = Transformations.switchMap(reloadDataTrigger) { repository.getNews() }
    }

    /**
     * Загрузка данных в случае если она еще не производилась
     */
    fun load() {
        if (reloadDataTrigger.value == true) {
            return
        }
        reloadDataTrigger.value = true
    }

    /**
     * Загрузка данных
     */
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