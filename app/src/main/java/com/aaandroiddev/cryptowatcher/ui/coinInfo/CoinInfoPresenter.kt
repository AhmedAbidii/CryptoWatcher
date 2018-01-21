package com.aaandroiddev.cryptowatcher.ui.coinInfo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.aaandroiddev.cryptowatcher.model.*
import com.aaandroiddev.cryptowatcher.model.classes.Coin
import com.aaandroiddev.cryptowatcher.model.classes.HistoData
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import com.aaandroiddev.cryptowatcher.utils.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinInfoPresenter @Inject constructor(private val context: Context,
                                            private val view: ICoinInfo.View,
                                            private val coinsController: CoinsController,
                                            private val networkRequests: NetworkRequests,
                                            private val graphMaker: GraphMaker,
                                            private val holdingsHandler: HoldingsHandler,
                                            private val resProvider: ResourceProvider) : ICoinInfo.Presenter {
    private val disposable = CompositeDisposable()
    private var coin: Coin? = null
    private var from: String? = null
    private var to: String? = null

    override fun onCreate(extras: Bundle) {
        if (extras[NAME] != null && extras[TO] != null) {
            from = extras.getString(NAME)
            to = extras.getString(TO)
            getCoinByName(from, to)
        }
    }

    private fun getCoinByName(from: String?, to: String?) {
   /*     disposable.add(Single.fromCallable { coinsController.getCoin(from!!, to!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinArrived(it) }, { onFindCoinError(it) }))*/
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(Coin(from = this.from!!, to = this.to!!))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onPriceUpdated(it) }, { Log.e("requestCoinInfo", it.toString()) }))
    }

    private fun onCoinArrived(coin: Coin) {
        this.coin = coin
        view.setTitle(coin.fullName)
        view.setLogo(coin.imgUrl)
        view.setMainPrice(coin.price)
        requestHisto(MONTH)
        setCoinInfo()
        setupHoldings()
    }

    private fun requestHisto(period: String) {
        disposable.add(networkRequests.getHistoPeriod(period, coin?.from, coin?.to)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onHistoReceived(it, period) }, { view.disableGraphLoading() }))
    }

    private fun onHistoReceived(histoList: ArrayList<HistoData>, period: String) {
        view.disableGraphLoading()
        view.drawChart(graphMaker.makeChart(histoList, period))
    }

    private fun setCoinInfo() {
        view.setOpen(coin!!.open24h)
        view.setHigh(coin!!.high24h)
        view.setLow(coin!!.low24h)
        view.setChange(coin!!.change24h)
        view.setChangePct(coin!!.changePct24h)
        view.setSupply(coin!!.supply)
        view.setMarketCap(coin!!.mktCap)
    }

    private fun setupHoldings() {
        val holdingData = holdingsHandler.isThereSuchHolding(coin?.from, coin?.to)
        if (holdingData != null) {
            view.setHoldingQuantity(holdingData.quantity.toString())
            view.setHoldingValue("$${getStringWithTwoDecimalsFromDouble(holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holdingData))}")

            val changePercent = holdingsHandler.getChangePercentByHoldingData(holdingData)
            view.setHoldingChangePercent("${getStringWithTwoDecimalsFromDouble(changePercent)}%")
            view.setHoldingChangePercentColor(getChangeColor(changePercent))

            view.setHoldingProfitLoss(getProfitLossTextBig(holdingsHandler.getChangeValueByHoldingData(holdingData), resProvider))
            val changeValue = holdingsHandler.getChangeValueByHoldingData(holdingData)
            view.setHoldingProfitValue("$${getStringWithTwoDecimalsFromDouble(changeValue)}")
            view.setHoldingProfitValueColor(getChangeColor(changeValue))

            view.setHoldingTradePrice("$${holdingData.price}")
            view.setHoldingTradeDate(formatLongDateToString(holdingData.date, DEFAULT_DATE_FORMAT))
            view.enableHoldings()
        } else {
            view.disableHoldings()
        }
    }

    private fun onFindCoinError(throwable: Throwable) {
        context.logDebug("getCoinByName error " + throwable.toString())
        requestCoinInfo(Coin(from = this.from!!, to = this.to!!))
    }

    private fun requestCoinInfo(coin: Coin) {
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coin)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onPriceUpdated(it) }, { Log.e("requestCoinInfo", it.toString()) }))
    }

    private fun onPriceUpdated(list: ArrayList<Coin>) {
        if (list.isNotEmpty()) {
            val arrivedCoin = list[0]
            coinsController.addAdditionalInfoToCoin(arrivedCoin)
            onCoinArrived(arrivedCoin)
        }
    }

    override fun onSpinnerItemClicked(position: Int) {
        if (coin != null) {
            view.enableGraphLoading()
            var period = ""
            when (position) {
                0 -> period = "1 hour"
                1 -> period = "12 hours"
                2 -> period = "24 hours"
                3 -> period = "3 days"
                4 -> period = "1 week"
                5 -> period = "1 month"
                6 -> period = "3 months"
                7 -> period = "6 months"
                8 -> period = "1 year"
            }
            requestHisto(period)
        }
    }

    override fun onStop() {
        disposable.clear()
    }

    override fun onAddTransactionClicked() {
        view.startAddTransactionActivity(coin?.from, coin?.to, coin?.price)
    }
}