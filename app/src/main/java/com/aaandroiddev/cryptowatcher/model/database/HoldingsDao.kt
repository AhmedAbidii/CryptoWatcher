package com.aaandroiddev.cryptowatcher.model.database

import android.arch.persistence.room.*
import com.aaandroiddev.cryptowatcher.model.classes.HoldingData
import io.reactivex.Flowable


@Dao
interface HoldingsDao {

    @Query("SELECT * FROM holdings")
    fun getAllHoldings(): Flowable<List<HoldingData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(holdingData: HoldingData)

    @Delete
    fun deleteHolding(holdingData: HoldingData)
}