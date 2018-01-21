package com.aaandroiddev.cryptowatcher.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.aaandroiddev.cryptowatcher.model.classes.Coin
import com.aaandroiddev.cryptowatcher.model.classes.HoldingData
import com.aaandroiddev.cryptowatcher.model.classes.InfoCoin
import com.aaandroiddev.cryptowatcher.model.classes.TopCoinData


@Database(entities = [
    Coin::class,
    InfoCoin::class,
    TopCoinData::class,
    HoldingData::class], version = 1)
abstract class CryptoWatcherDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDao

    abstract fun allCoinsDao(): AllCoinsDao

    abstract fun topCoinsDao(): TopCoinsDao

    abstract fun holdingsDao(): HoldingsDao
}