package com.aaandroiddev.cryptowatcher.di

import com.aaandroiddev.cryptowatcher.model.BASE_CRYPTOCOMPARE_URL
import com.aaandroiddev.cryptowatcher.model.network.CoinMarketCapApi
import com.aaandroiddev.cryptowatcher.model.network.CryptoCompareAPI
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client : OkHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
            Retrofit.Builder().baseUrl(BASE_CRYPTOCOMPARE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

    @Provides
    @Singleton
    fun provideCryptoCompareAPI(retrofit: Retrofit): CryptoCompareAPI = retrofit.create(CryptoCompareAPI::class.java)

    @Provides
    @Singleton
    fun provideCoinMarketCapApi(retrofit: Retrofit): CoinMarketCapApi = retrofit.create(CoinMarketCapApi::class.java)

    @Provides
    @Singleton
    fun provideNetworkRequests(cryptoCompareAPI: CryptoCompareAPI, coinMarketCapApi: CoinMarketCapApi) =
            NetworkRequests(cryptoCompareAPI, coinMarketCapApi)
}