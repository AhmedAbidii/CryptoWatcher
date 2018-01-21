package com.aaandroiddev.cryptowatcher.ui.settings

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerActivity
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    @PerActivity
    fun provideView(settingsActivity: SettingsActivity): Settings.View = settingsActivity

    @Provides
    @PerActivity
    fun providePresenter(view: Settings.View,
                         context: Context,
                         resourceProvider: ResourceProvider): Settings.Presenter = SettingsPresenter(view, context, resourceProvider)
}