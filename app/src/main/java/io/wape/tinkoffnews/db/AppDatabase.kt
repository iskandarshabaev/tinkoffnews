package io.wape.tinkoffnews.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import io.wape.tinkoffnews.db.dao.NewsDao
import io.wape.tinkoffnews.db.entity.NewsEntity

@Database(entities = arrayOf(NewsEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        val DATABASE_NAME = "app_db.db"
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, DATABASE_NAME)
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun newsDao(): NewsDao
}
