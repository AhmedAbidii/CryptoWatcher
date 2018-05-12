package com.aaandroiddev.cryptowatcher.ui.news

import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Tweet

interface INews {


    interface View {
        fun hideLoginBtn()
        fun showLoginBtn()
        fun showRecView()
        fun updateInsertedTweets(startPos: Int, count: Int)
        fun showLoading()
        fun hideLoading()
        fun hideSwipeRefreshing()
        fun showSearchDialog()
        fun showEmptyNews()
        fun hideEmptyNews()
        fun showFab()
        fun hideFab()
    }

    interface Presenter {
        fun onCreate(tweets: ArrayList<Tweet>)
        fun onSuccessLogin(result: Result<TwitterSession>?)
        fun onStart()
        fun onStop()
        fun onSwipeUpdate()
        fun onScrolled(dy: Int, childCount: Int, itemCount: Int, firstVisiblePosition: Int)
        fun onFabClicked()
    }

}