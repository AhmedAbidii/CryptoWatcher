package com.aaandroiddev.cryptowatcher.model.classes

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "holdings", primaryKeys = arrayOf("from_coin", "to_currency", "transaction_date"))
data class HoldingData(@ColumnInfo(name = "from_coin") var from: String,
                       @ColumnInfo(name = "to_currency") var to: String,
                       var quantity: Float = 0f,
                       var price: Float = 0f,
                       @ColumnInfo(name = "transaction_date") var date: Long)