package io.wape.tinkoffnews.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import io.wape.tinkoffnews.api.models.PublicationDate
import io.wape.tinkoffnews.db.dao.NewsTypeConverters

@Entity(tableName = "news")
@TypeConverters(NewsTypeConverters::class)
class NewsEntity {

    @SerializedName("id")
    @PrimaryKey()
    var id: Long = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("text")
    var text: String = ""
    @SerializedName("publicationDate")
    var publicationDate: PublicationDate = PublicationDate(0)
    @SerializedName("bankInfoTypeId")
    var bankInfoTypeId: Long = 0
    @SerializedName("content")
    var content: String = ""

}
