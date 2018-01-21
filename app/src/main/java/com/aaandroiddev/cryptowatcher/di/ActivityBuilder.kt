package com.aaandroiddev.cryptowatcher.di

import com.aaandroiddev.cryptowatcher.ui.addCoin.AddCoinActivity
import com.aaandroiddev.cryptowatcher.ui.addCoin.AddCoinModule
import com.aaandroiddev.cryptowatcher.ui.addTransaction.AddTransactionActivity
import com.aaandroiddev.cryptowatcher.ui.addTransaction.AddTransactionModule
import com.aaandroiddev.cryptowatcher.ui.coinAllocation.CoinAllocationActivity
import com.aaandroiddev.cryptowatcher.ui.coinAllocation.CoinAllocationModule
import com.aaandroiddev.cryptowatcher.ui.coinInfo.CoinInfoActivity
import com.aaandroiddev.cryptowatcher.ui.coinInfo.CoinInfoModule
import com.aaandroiddev.cryptowatcher.ui.holdings.HoldingsActivity
import com.aaandroiddev.cryptowatcher.ui.holdings.HoldingsModule
import com.aaandroiddev.cryptowatcher.ui.main.MainActivity
import com.aaandroiddev.cryptowatcher.ui.main.MainFragmentProvider
import com.aaandroiddev.cryptowatcher.ui.main.MainModule
import com.aaandroiddev.cryptowatcher.ui.settings.SettingsActivity
import com.aaandroiddev.cryptowatcher.ui.settings.SettingsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class, MainFragmentProvider::class))
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(AddCoinModule::class))
    abstract fun bindAddCoinActivity(): AddCoinActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(CoinInfoModule::class))
    abstract fun bindCoinInfoActivity(): CoinInfoActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(CoinAllocationModule::class))
    abstract fun bindCoinAllocationActivity(): CoinAllocationActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(AddTransactionModule::class))
    abstract fun bindAddTransactionActivity(): AddTransactionActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(HoldingsModule::class))
    abstract fun bindHoldingsActivity(): HoldingsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun bindSettingsActivity(): SettingsActivity

}