package com.aaandroiddev.cryptowatcher.ui.coinAllocation

import com.aaandroiddev.cryptowatcher.di.PerActivity
import com.aaandroiddev.cryptowatcher.model.PieMaker
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import dagger.Module
import dagger.Provides

@Module
class CoinAllocationModule {
    @Provides
    @PerActivity
    fun provideView(coinAllocationActivity: CoinAllocationActivity): ICoinAllocation.View = coinAllocationActivity

    @Provides
    @PerActivity
    fun providePresenter(view: ICoinAllocation.View,
                         pieMaker: PieMaker,
                         db: CryptoWatcherDatabase): ICoinAllocation.Presenter = CoinAllocationPresenter(view, pieMaker, db)
}