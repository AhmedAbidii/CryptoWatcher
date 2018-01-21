package com.aaandroiddev.cryptowatcher.model.classes

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class AllCoinsResponse(
        @SerializedName("Response") val response: String,
        @SerializedName("Message") val message: String,
        @SerializedName("BaseImageUrl") val baseImageUrl: String,
        @SerializedName("BaseLinkUrl") val baseLinkUrl: String,
        @SerializedName("Data") val data: JsonObject,
        @SerializedName("Type") val type: Int)