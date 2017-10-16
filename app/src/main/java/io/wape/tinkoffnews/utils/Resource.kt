package io.wape.tinkoffnews.utils

/**
 * Статусы выполнения задачи
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

/**
 * Контейнер для данных в LivaData
 * @param status статус выполнения {Status}
 * @param data результат выполнения(если есть)
 * @param throwable ошибка выполнения(если есть)
 */
data class Resource<out T>(val status: Status?, val data: T?, val throwable: Throwable?) {

    /**
     * Вспомогательные методы для создания Resource
     */
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, throwable)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

}