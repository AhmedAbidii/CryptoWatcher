package com.aaandroiddev.cryptowatcher.model.network

import com.aaandroiddev.cryptowatcher.model.classes.AllCoinsResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CryptoCompareAPI {

    @GET
    fun getCoinsList(@Url url: String): Single<AllCoinsResponse>

    @GET("pricemultifull")
    fun getPrice(@Query("fsyms") from: String, @Query("tsyms") to: String): Single<JsonObject>

    @GET("{period}")
    fun getHistoPeriod(@Path("period") period: String,
                       @Query("fsym") from: String?,
                       @Query("tsym") to: String?,
                       @Query("limit") limit: Int,
                       @Query("aggregate") aggregate: Int): io.reactivex.Single<JsonObject>

    @GET("top/pairs")
    fun getPairs(@Query("fsym") from: String): io.reactivex.Single<JsonObject>
}