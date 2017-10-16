package io.wape.tinkoffnews.screens.newsdetails

import android.arch.lifecycle.*
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.repository.NewsRepository
import io.wape.tinkoffnews.utils.Resource

/**
 * ViewModel для NewsContentFragment
 * @param repository репозиторий для работы с новостями
 * Передавать например так: NewsContentViewModel(repositoryProvider.provideNews(yourContext))
 */
class NewsContentViewModel(repository: NewsRepository) : ViewModel() {

    /**
     * Триггер для загрузки данных
     */
    private val idTrigger = MutableLiveData<Long>().apply { value = 0 }

    /**
     * LiveData для данных необходимых для отрисовки NewsContentFragment
     */
    val newsContent: LiveData<Resource<NewsEntity>>

    /**
     * Создается newsContent
     * После того как значение idTrigger.value будет изменено,
     * сработает триггер и вызовется метод repository.getNewsContent(it)
     */
    init {
        newsContent = Transformations.switchMap(idTrigger) { repository.getNewsContent(it) }
    }

    /**
     * Перезагрузка содержимого
     */
    fun reload(id: Long) {
        idTrigger.value = id
    }

    /**
     * Загрузка данных, если данные по id еще не запрашивались
     */
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