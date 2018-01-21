package com.aaandroiddev.cryptowatcher.ui.topCoins

import com.aaandroiddev.cryptowatcher.model.classes.TopCoinData

interface ITopCoins {

    interface View {
        fun updateRecyclerView()
        fun hideRefreshing()
        fun startCoinInfoActivity(name: String?)
    }

    interface Presenter {
        fun onCreate(coins: ArrayList<TopCoinData>)
        fun onCoinClicked(coin: TopCoinData)
        fun onSwipeUpdate()
        fun onStart()
        fun onAddCoinClicked(coin: TopCoinData, itemView: android.view.View)
        fun onStop()
    }
}