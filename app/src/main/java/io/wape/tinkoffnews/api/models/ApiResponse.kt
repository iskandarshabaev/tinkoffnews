package io.wape.tinkoffnews.api.models

import com.google.gson.annotations.SerializedName

class ApiResponse<T> {

    @SerializedName("resultCode")
    var resultCode: String = ""
    @SerializedName("trackingID")
    var trackingID: Long = 0
    @SerializedName("payload")
    var payload: T? = null

}
