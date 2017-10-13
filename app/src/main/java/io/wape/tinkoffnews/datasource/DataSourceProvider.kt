package io.wape.tinkoffnews.datasource

import android.content.Context
import io.wape.tinkoffnews.db.AppDatabase


object DataSourceProvider {

    @Volatile private var sLocalDataSource: NewsLocalDataSource? = null
    @Volatile private var sNetworkDataSource: NewsNetworkDataSource? = null

    fun newsLocal(context: Context): NewsLocalDataSource {
        var instance = sLocalDataSource
        if (instance == null) {
            synchronized(DataSourceProvider::class.java) {
                instance = sLocalDataSource
                if (instance == null) {
                    sLocalDataSource = NewsLocalDataSource(AppDatabase.getInstance(context))
                    instance = sLocalDataSource
                }
            }
        }
        return instance!!
    }

    fun newsNetwork(): NewsNetworkDataSource {
        var instance = sNetworkDataSource
        if (instance == null) {
            synchronized(DataSourceProvider::class.java) {
                instance = sNetworkDataSource
                if (instance == null) {
                    sNetworkDataSource = NewsNetworkDataSource()
                    instance = sNetworkDataSource
                }
            }
        }
        return instance!!
    }

}
