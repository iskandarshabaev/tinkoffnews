package io.wape.tinkoffnews.api.models

import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PublicationDate {

    companion object {
        var dateFormat: DateFormat =  SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault())
    }

    @SerializedName("milliseconds")
    var milliseconds: Long = 0

    constructor(milliseconds: Long) {
        this.milliseconds = milliseconds
    }

    fun date(): String {
        return dateFormat.format(milliseconds)
    }

}
