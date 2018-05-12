package com.aaandroiddev.cryptowatcher

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.aaandroiddev.cryptowatcher.di.DaggerAppComponent
import com.aaandroiddev.cryptowatcher.model.LocaleManager
import com.twitter.sdk.android.core.Twitter
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class CryptoWatcherApp : Application(), HasActivityInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        Twitter.initialize(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(base!!))
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }
}