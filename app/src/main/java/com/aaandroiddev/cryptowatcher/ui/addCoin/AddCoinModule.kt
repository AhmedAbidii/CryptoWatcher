package com.aaandroiddev.cryptowatcher.ui.addCoin

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerActivity
import com.aaandroiddev.cryptowatcher.model.CoinsController
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides


@Module
class AddCoinModule {

    @Provides
    @PerActivity
    fun provideView(addCoinActivity: AddCoinActivity): IAddCoin.View = addCoinActivity

    @Provides
    @PerActivity
    fun providePresenter(context: Context,
                         view: IAddCoin.View,
                         coinsController: CoinsController,
                         networkRequests: NetworkRequests,
                         resProvider: ResourceProvider,
                         db: CryptoWatcherDatabase): IAddCoin.Presenter =
            AddCoinPresenter(context, view, coinsController, networkRequests, resProvider, db)
}