package com.aaandroiddev.cryptowatcher.model.classes

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "all_coins")
data class InfoCoin (
        @PrimaryKey @SerializedName("Id") var coinId: String,
        @SerializedName("Url") var url: String = "",
        @SerializedName("ImageUrl") var imageUrl: String = "",
        @SerializedName("Name") var name: String = "",
        @SerializedName("CoinName") var coinName: String = "",
        @SerializedName("FullName") var fullName: String = "",
        @SerializedName("Algorithm") var algorithm: String = "",
        @SerializedName("ProofType") var proofType: String = "",
        @SerializedName("FullyPremined") var fullyPremined: String = "",
        @SerializedName("TotalCoinSupply") var totalCoinSupply: String = "",
        @SerializedName("PreMinedValue") var preMinedValue: String = "",
        @SerializedName("TotalCoinsFreeFloat") var totalCoinsFreeFloat: String = "",
        @SerializedName("SortOrder") var sortOrder: String = "")