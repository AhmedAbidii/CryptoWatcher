package com.aaandroiddev.cryptowatcher.model.classes

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "coins", primaryKeys = ["from_name", "to_name"])
data class Coin(
        @ColumnInfo(name = "from_name")
        var from: String,
        @ColumnInfo(name = "to_name")
        var to: String,
        var imgUrl: String = "",
        var fullName: String = "",
        var selected: Boolean = false,
        var fromSymbol: String = "",
        var toSymbol: String = "",
        var market: String = "",
        var price: String = "",
        var priceRaw: Float = 0f,
        var lastUpdate: String = "",
        var lastUpdateRaw: Float = 0f,
        var lastVolume: String = "",
        var lastVolumeRaw: Float = 0f,
        var lastVolumeTo: String = "",
        var lastVolumeToRaw: Float = 0f,
        var lastTradeId: Float = 0f,
        var volume24h: String = "",
        var volume24hRaw: Float = 0f,
        var volume24hTo: String = "",
        var volume24hToRaw: Float = 0f,
        var open24h: String = "",
        var open24hRaw: Float = 0f,
        var high24h: String = "",
        var high24hRaw: Float = 0f,
        var low24h: String = "",
        var low24hRaw: Float = 0f,
        var lastMarket: String = "",
        var change24h: String = "",
        var change24hRaw: Float = 0f,
        var changePct24h: String = "",
        var changePct24hRaw: Float = 0f,
        var supply: String = "",
        var supplyRaw: Float = 0f,
        var mktCap: String = "",
        var mktCapRaw: Float = 0f,
        var flags: String = "")