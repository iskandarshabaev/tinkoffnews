package io.wape.tinkoffnews.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class AppExecutors(val diskIO: Executor, val networkIO: Executor, val mainThread: Executor)

private class MainThreadExecutor : Executor {
    private var mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}

object appExecutorsDefault : AppExecutors(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor())

