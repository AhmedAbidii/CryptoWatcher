package com.aaandroiddev.cryptowatcher.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.aaandroiddev.cryptowatcher.model.*
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.model.database.DBController
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [(ViewModelSubComponent::class)])
class AppModule {
    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideDatabase(application: Application): CryptoWatcherDatabase =
            Room.databaseBuilder(application, CryptoWatcherDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @Singleton
    fun provideDBController(db: CryptoWatcherDatabase) = DBController(db)

    @Provides
    @Singleton
    fun provideResourceProvider(application: Application) = ResourceProvider(application)

    @Provides
    @Singleton
    fun provideCoinsController(dbController: DBController, db: CryptoWatcherDatabase) = CoinsController(dbController, db)

    @Provides
    @Singleton
    fun provideMultiSelector(resourceProvider: ResourceProvider) = MultiSelector(resourceProvider)

    @Provides
    @Singleton
    fun providePageController() = PageController()

    @Provides
    @Singleton
    fun provideGraphMaker(resourceProvider: ResourceProvider) = GraphMaker(resourceProvider)

    @Provides
    @Singleton
    fun providePieMaker(resourceProvider: ResourceProvider, holdingsHandler: HoldingsHandler) = PieMaker(resourceProvider, holdingsHandler)

    @Provides
    @Singleton
    fun provideHoldingsHandler(db: CryptoWatcherDatabase) = HoldingsHandler(db)
}