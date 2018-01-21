package com.aaandroiddev.cryptowatcher.ui.main

import com.aaandroiddev.cryptowatcher.di.PerFragment
import com.aaandroiddev.cryptowatcher.ui.coins.CoinsFragment
import com.aaandroiddev.cryptowatcher.ui.coins.CoinsModule
import com.aaandroiddev.cryptowatcher.ui.news.NewsFragment
import com.aaandroiddev.cryptowatcher.ui.news.NewsModule
import com.aaandroiddev.cryptowatcher.ui.topCoins.TopCoinsFragment
import com.aaandroiddev.cryptowatcher.ui.topCoins.TopCoinsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainFragmentProvider {

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(CoinsModule::class))
    abstract fun provideCoinFragmentFactory(): CoinsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(TopCoinsModule::class))
    abstract fun provideTopCoinsFragmentFactory(): TopCoinsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(NewsModule::class))
    abstract fun provideNewsFragmentFactory(): NewsFragment
}