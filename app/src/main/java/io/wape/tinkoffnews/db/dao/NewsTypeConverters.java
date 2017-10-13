package io.wape.tinkoffnews.db.dao;

import android.arch.persistence.room.TypeConverter;

import io.wape.tinkoffnews.api.models.PublicationDate;

public class NewsTypeConverters {

    @TypeConverter
    public static Long publicationDateToTimestamp(PublicationDate data) {
        if (data != null) {
            return data.getMilliseconds();
        }
        return 0L;
    }

    @TypeConverter
    public static PublicationDate timestampToPublicationDate(Long timestamp) {
        return new PublicationDate(timestamp);
    }

}
