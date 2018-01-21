package com.aaandroiddev.cryptowatcher.model.classes

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "top_coins")
data class TopCoinData(
        var id: String? = "",
        @PrimaryKey var name: String,
        var symbol: String? = "",
        var rank: Int? = 0,
        var price_usd: String? = "",
        var price_btc: String? = "",
        @SerializedName("24h_volume_usd") var vol24Usd: String? = "",
        var market_cap_usd: String? = "",
        var available_supply: String? = "",
        var total_supply: String? = "",
        var percent_change_1h: String? = "",
        var percent_change_24h: String? = "",
        var percent_change_7d: String? = "",
        var last_updated: String? = "",
        var imgUrl: String? = "" )