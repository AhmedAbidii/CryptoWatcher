package com.aaandroiddev.cryptowatcher.model.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aaandroiddev.cryptowatcher.model.classes.InfoCoin
import io.reactivex.Flowable


@Dao
interface AllCoinsDao {

    @Query("SELECT * FROM all_coins")
    fun getAllCoins(): Flowable<List<InfoCoin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: InfoCoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<InfoCoin>)

    @Query("DELETE FROM all_coins")
    fun clearAllCoins()
}