package com.aaandroiddev.cryptowatcher.model.network

import com.aaandroiddev.cryptowatcher.model.classes.TopCoinData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CoinMarketCapApi {

    @GET
    fun getTopCoins(@Url url: String, @Query("limit") limit: Int): Single<List<TopCoinData>>
}