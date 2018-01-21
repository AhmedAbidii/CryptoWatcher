package com.aaandroiddev.cryptowatcher.model.classes

import com.google.gson.annotations.SerializedName

data class HistoData(
        val time: Long,
        val close: Float,
        val high: Float,
        val low: Float,
        val open: Float,
        @SerializedName("volumefrom") val volumeFrom: Float,
        @SerializedName("volumeto") val volumeTo: Float)