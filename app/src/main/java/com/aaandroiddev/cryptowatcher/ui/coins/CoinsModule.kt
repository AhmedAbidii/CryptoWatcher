package com.aaandroiddev.cryptowatcher.ui.coins

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerFragment
import com.aaandroiddev.cryptowatcher.model.CoinsController
import com.aaandroiddev.cryptowatcher.model.HoldingsHandler
import com.aaandroiddev.cryptowatcher.model.MultiSelector
import com.aaandroiddev.cryptowatcher.model.PageController
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class CoinsModule {

    @Provides
    @PerFragment
    fun provideView(coinsFragment: CoinsFragment): ICoins.View = coinsFragment

    @Provides
    @PerFragment
    fun providePresenter(context: Context,
                         view: ICoins.View,
                         networkRequests: NetworkRequests,
                         coinsController: CoinsController,
                         db: CryptoWatcherDatabase,
                         resProvider: ResourceProvider,
                         pageController: PageController,
                         multiSelector: MultiSelector,
                         holdingsHandler: HoldingsHandler): ICoins.Presenter =
            CoinsPresenter(context, view, networkRequests, coinsController, db, resProvider,
                    pageController, multiSelector, holdingsHandler)
}