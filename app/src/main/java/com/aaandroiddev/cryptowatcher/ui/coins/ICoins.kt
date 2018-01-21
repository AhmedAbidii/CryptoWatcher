package com.aaandroiddev.cryptowatcher.ui.coins

import com.aaandroiddev.cryptowatcher.model.classes.Coin

interface ICoins {

    interface View {
        fun updateRecyclerView()
        fun hideRefreshing()
        fun enableSwipeToRefresh()
        fun disableSwipeToRefresh()
        fun startCoinInfoActivity(name: String?, to: String?)
        fun enableTotalHoldings()
        fun disableTotalHoldings()
        fun setTotalHoldingsValue(total: String)
        fun setTotalHoldingsChangePercent(percent: String)
        fun setTotalHoldingsChangePercentColor(color: Int)
        fun setTotalHoldingsChangeValue(value: String)
        fun setTotalHoldingsChangeValueColor(color: Int)
        fun setAllTimeProfitLossString(text: String)
        fun startHoldingsActivity()
        fun startAllocationsActivity()
        fun enableEmptyText()
        fun disableEmptyText()
    }

    interface Presenter {
        fun onCreate(coins: ArrayList<Coin>)
        fun onStart()
        fun onViewCreated()
        fun onStop()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: Coin)
        fun onHoldingsClicked()
        fun onAllocationsClicked()
    }
}