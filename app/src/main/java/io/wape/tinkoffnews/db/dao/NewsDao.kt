package io.wape.tinkoffnews.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.wape.tinkoffnews.db.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY publicationDate DESC")
    fun loadAllNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news WHERE id = :arg0")
    fun loadNewsContent(id: Long): LiveData<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: NewsEntity)

}