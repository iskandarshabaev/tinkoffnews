package io.wape.tinkoffnews.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Класс с Executor
 */
abstract class AppExecutors(val diskIO: Executor, val networkIO: Executor, val mainThread: Executor)

/**
 * Запуск комманд в главном потоке
 */
private class MainThreadExecutor : Executor {
    private var mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}

/**
 * Содержит три Executor для работы с дисковыми, сетевыми операциями и
 * выполнения комманд в главном потоке
 */
object appExecutorsDefault : AppExecutors(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor())

