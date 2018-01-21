package com.aaandroiddev.cryptowatcher.ui.news

import android.content.Context
import com.aaandroiddev.cryptowatcher.di.PerFragment
import dagger.Module
import dagger.Provides

@Module
class NewsModule {

    @Provides @PerFragment
    fun provideView(newsFragment: NewsFragment): INews.View = newsFragment

    @Provides @PerFragment
    fun providePresenter(view: INews.View, context: Context): INews.Presenter = NewsPresenter(view, context)

}