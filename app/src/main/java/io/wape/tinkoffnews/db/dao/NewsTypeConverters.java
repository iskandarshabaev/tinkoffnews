package io.wape.tinkoffnews.db.dao;

import android.arch.persistence.room.TypeConverter;

import io.wape.tinkoffnews.api.models.PublicationDate;

/**
 * Конвертер для PublicationDate.
 */
public class NewsTypeConverters {

    /**
     * Конвертирует класс PublicationDate в long значение
     *
     * @param data Класс с датой внутри
     * @return дата типа long из PublicationDate
     */
    @TypeConverter
    public static Long publicationDateToTimestamp(PublicationDate data) {
        if (data != null) {
            return data.getMilliseconds();
        }
        return 0L;
    }

    /**
     * Конвертирует Long timestamp в PublicationDate
     *
     * @param timestamp дата в Long
     * @return дата в PublicationDate
     */
    @TypeConverter
    public static PublicationDate timestampToPublicationDate(Long timestamp) {
        return new PublicationDate(timestamp);
    }

}
