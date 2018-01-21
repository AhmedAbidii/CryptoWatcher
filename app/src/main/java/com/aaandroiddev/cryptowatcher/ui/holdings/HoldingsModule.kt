package com.aaandroiddev.cryptowatcher.ui.holdings

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerActivity
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class HoldingsModule {
    @Provides @PerActivity
    fun provideView(holdingsActivity: HoldingsActivity): IHoldings.View = holdingsActivity

    @Provides @PerActivity
    fun providePresenter(view: IHoldings.View,
                         db: CryptoWatcherDatabase,
                         context: Context,
                         resourceProvider: ResourceProvider): IHoldings.Presenter =
            HoldingsPresenter(view, db, context, resourceProvider)
}