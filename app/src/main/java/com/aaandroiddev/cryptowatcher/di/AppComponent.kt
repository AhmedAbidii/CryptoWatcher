package com.aaandroiddev.cryptowatcher.di

import android.app.Application
import com.aaandroiddev.cryptowatcher.CryptoWatcherApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
        AppModule::class, NetworkModule::class, ActivityBuilder::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: CryptoWatcherApp)
}