package com.aaandroiddev.cryptowatcher.ui.main

import com.aaandroiddev.cryptowatcher.di.PerActivity
import com.aaandroiddev.cryptowatcher.model.MultiSelector
import com.aaandroiddev.cryptowatcher.model.PageController
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides @PerActivity
    fun provideView(mainActivity: MainActivity): IMain.View = mainActivity

    @Provides @PerActivity
    fun providePresenter(view: IMain.View,
                         multiSelector: MultiSelector,
                         pageController: PageController): IMain.Presenter =
            MainPresenter(view, multiSelector, pageController)
}