package io.wape.tinkoffnews.api.models

import com.google.gson.annotations.SerializedName

class Resp<T> {

    @SerializedName("resultCode")
    var resultCode: String = ""
    @SerializedName("trackingID")
    var trackingID: Long = 0
    @SerializedName("payload")
    var payload: T? = null

    constructor(resultCode: String, trackingID: Long, payload: T) {
        this.resultCode = resultCode
        this.trackingID = trackingID
        this.payload = payload
    }

    constructor() {}
}
