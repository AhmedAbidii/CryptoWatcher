package com.aaandroiddev.cryptowatcher.ui.coinInfo

import android.os.Bundle
import com.github.mikephil.charting.data.CandleData

interface ICoinInfo {


    interface View {
        fun setTitle(title: String)
        fun setLogo(url: String)
        fun setMainPrice(price: String)
        fun drawChart(line: CandleData)
        fun setOpen(open: String)
        fun setHigh(high: String)
        fun setLow(low: String)
        fun setChange(change: String)
        fun setChangePct(pct: String)
        fun setSupply(supply: String)
        fun setMarketCap(cap: String)
        fun enableGraphLoading()
        fun disableGraphLoading()
        fun startAddTransactionActivity(from: String?, to: String?, price: String?)
        fun setHoldingQuantity(quantity: String)
        fun setHoldingValue(value: String)
        fun setHoldingChangePercent(pct: String)
        fun setHoldingChangePercentColor(color: Int)
        fun setHoldingProfitLoss(profitLoss: String)
        fun setHoldingProfitValue(value: String)
        fun setHoldingProfitValueColor(color: Int)
        fun setHoldingTradePrice(price: String)
        fun setHoldingTradeDate(date: String)
        fun enableHoldings()
        fun disableHoldings()
    }

    interface Presenter {
        fun onCreate(extras: Bundle)
        fun onSpinnerItemClicked(position: Int)
        fun onAddTransactionClicked()
        fun onStop()
    }
}