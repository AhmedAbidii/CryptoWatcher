package com.aaandroiddev.cryptowatcher.ui.topCoins

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerFragment
import com.aaandroiddev.cryptowatcher.model.CoinsController
import com.aaandroiddev.cryptowatcher.model.PageController
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides


@Module
class TopCoinsModule {

    @Provides @PerFragment
    fun provideView(topCoinsFragment: TopCoinsFragment): ITopCoins.View = topCoinsFragment

    @Provides @PerFragment
    fun providePresenter(context: Context,
                         view: ITopCoins.View,
                         db: CryptoWatcherDatabase,
                         networkRequests: NetworkRequests,
                         coinsController: CoinsController,
                         resProvider: ResourceProvider,
                         pageController: PageController): ITopCoins.Presenter =
            TopCoinsPresenter(context, view, db, networkRequests, coinsController, resProvider, pageController)
}