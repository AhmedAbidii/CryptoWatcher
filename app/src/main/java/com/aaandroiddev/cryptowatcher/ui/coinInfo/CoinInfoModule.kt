package com.aaandroiddev.cryptowatcher.ui.coinInfo

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerActivity
import com.aaandroiddev.cryptowatcher.model.CoinsController
import com.aaandroiddev.cryptowatcher.model.GraphMaker
import com.aaandroiddev.cryptowatcher.model.HoldingsHandler
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import com.aaandroiddev.cryptowatcher.ui.coinInfo.CoinInfoActivity
import com.aaandroiddev.cryptowatcher.ui.coinInfo.CoinInfoPresenter
import com.aaandroiddev.cryptowatcher.ui.coinInfo.ICoinInfo
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class CoinInfoModule {
    @Provides @PerActivity
    fun provideView(coinInfoActivity: CoinInfoActivity): ICoinInfo.View = coinInfoActivity

    @Provides @PerActivity
    fun providePresenter(context: Context,
                         view: ICoinInfo.View,
                         coinsController: CoinsController,
                         networkRequests: NetworkRequests,
                         graphMaker: GraphMaker,
                         holdingsHandler: HoldingsHandler,
                         resourceProvider: ResourceProvider): ICoinInfo.Presenter =
            CoinInfoPresenter(context, view, coinsController, networkRequests, graphMaker, holdingsHandler, resourceProvider)

}