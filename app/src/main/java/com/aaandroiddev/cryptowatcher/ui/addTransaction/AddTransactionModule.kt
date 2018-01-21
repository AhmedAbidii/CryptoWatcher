package com.aaandroiddev.cryptowatcher.ui.addTransaction

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerActivity
import com.aaandroiddev.cryptowatcher.model.database.DBController
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class AddTransactionModule {
    @Provides @PerActivity
    fun provideView(addTransactionActivity: AddTransactionActivity): IAddTransaction.View = addTransactionActivity

    @Provides @PerActivity
    fun providePresenter(view: IAddTransaction.View,
                         context: Context,
                         resourceProvider: ResourceProvider,
                         dbController: DBController): IAddTransaction.Presenter =
            AddTransactionPresenter(view, context, resourceProvider, dbController)
}