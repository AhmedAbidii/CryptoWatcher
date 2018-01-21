package com.aaandroiddev.cryptowatcher.ui.coins

import android.content.Context
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.*
import com.aaandroiddev.cryptowatcher.model.classes.Coin
import com.aaandroiddev.cryptowatcher.model.classes.HoldingData
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import com.aaandroiddev.cryptowatcher.model.rxbus.*
import com.aaandroiddev.cryptowatcher.ui.main.SortDialog
import com.aaandroiddev.cryptowatcher.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinsPresenter @Inject constructor(private val context: Context,
                                         private val view: ICoins.View,
                                         private val networkRequests: NetworkRequests,
                                         private val coinsController: CoinsController,
                                         private val db: CryptoWatcherDatabase,
                                         private val resProvider: ResourceProvider,
                                         private val pageController: PageController,
                                         private val multiSelector: MultiSelector,
                                         private val holdingsHandler: HoldingsHandler) : ICoins.Presenter {
    override fun onViewCreated() {

    }

    private val prefs = Preferences(context)
    private val disposable = CompositeDisposable()
    private var coins: ArrayList<Coin> = ArrayList()
    private var holdings: ArrayList<HoldingData> = ArrayList()
    private var isRefreshing = false
    private var isFirstStart = true
    override fun onCreate(coins: ArrayList<Coin>) {
        this.coins = coins
    }

    override fun onStart() {
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        addCoinsChangesObservable()
        addHoldingsChangesObservable()
        setupRxBusEventsListeners()
        addOnPageChangedObservable()

    }

    private fun addOnPageChangedObservable() {
        disposable.add(pageController.getPageObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onPageChanged(it) })
    }

    private fun onPageChanged(position: Int) {
        if (position != COINS_FRAGMENT_PAGE_POSITION) {
            disableSelected()
        }
    }

    private fun disableSelected() {
        if (multiSelector.atLeastOneIsSelected) {
            coins.forEach { if (it.selected) it.selected = false }
            view.updateRecyclerView()
            multiSelector.atLeastOneIsSelected = false
        }
    }

    private fun setupRxBusEventsListeners() {
        disposable.add(RxBus.listen(OnDeleteCoinsMenuItemClickedEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onDeleteClicked() })
        disposable.add(RxBus.listen(CoinsSortMethodUpdated::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onSortMethodUpdated() })
    }

    private fun onDeleteClicked() {
        val coinsToDelete = coins.filter { it.selected }
        if (coinsToDelete.isNotEmpty()) {
            disableSelected()
            holdingsHandler.removeHoldings(coinsToDelete)
            coinsController.deleteCoins(coinsToDelete)
            RxBus.publish(MainCoinsListUpdatedEvent())
            context.toastShort(if (coinsToDelete.size > 1) resProvider.getString(R.string.coins_deleted)
            else resProvider.getString(R.string.coin_deleted))
        }
    }

    private fun onSortMethodUpdated() {
        if (coins.isNotEmpty() && coins.size > 1) {
            sortCoinsBySelectedSortMethod()
        }
    }

    private fun sortCoinsBySelectedSortMethod() {
        when (prefs.sortBy) {
            SortDialog.SORT_BY_NAME -> coins.sortBy { it.from }
            SortDialog.SORT_BY_PRICE_INCREASE -> coins.sortBy { it.priceRaw }
            SortDialog.SORT_BY_PRICE_DECREASE -> coins.sortByDescending { it.priceRaw }
            SortDialog.SORT_BY_24H_PRICE_INCREASE -> coins.sortBy { it.changePct24hRaw }
            SortDialog.SORT_BY_24H_PRICE_DECREASE -> coins.sortByDescending { it.changePct24hRaw }
        }
        view.updateRecyclerView()
    }

    private fun addHoldingsChangesObservable() {
        disposable.add(db.holdingsDao().getAllHoldings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onHoldingsUpdate(it) }))
    }

    private fun onHoldingsUpdate(updatedHoldings: List<HoldingData>) {
        holdings.clear()
        if (updatedHoldings.isNotEmpty()) {
            holdings.addAll(updatedHoldings)
            updateHoldings()
        } else {
            view.disableTotalHoldings()
        }
    }

    private fun updateHoldings() {
        if (holdings.isNotEmpty()) {
            setTotalHoldingValue()
            setTotalHoldingsChangePercent()
            setTotalHoldingsChangeValue()
            view.enableTotalHoldings()
        }
    }

    private fun setTotalHoldingValue() {
        view.setTotalHoldingsValue("$ ${getStringWithTwoDecimalsFromDouble(holdingsHandler.getTotalValueWithCurrentPrice())}")
    }

    private fun setTotalHoldingsChangePercent() {
        val totalChangePercent = holdingsHandler.getTotalChangePercent()
        view.setTotalHoldingsChangePercent("${getStringWithTwoDecimalsFromDouble(totalChangePercent)}%")
        view.setTotalHoldingsChangePercentColor(getChangeColor(totalChangePercent))
    }

    private fun setTotalHoldingsChangeValue() {
        val totalChangeValue = holdingsHandler.getTotalChangeValue()
        view.setTotalHoldingsChangeValue("$${getStringWithTwoDecimalsFromDouble(totalChangeValue)}")
        view.setTotalHoldingsChangeValueColor(getChangeColor(totalChangeValue))
        setAllTimeProfitLossString(totalChangeValue)
    }

    private fun setAllTimeProfitLossString(change: Float) {
        view.setAllTimeProfitLossString(getProfitLossText(change))
    }

    private fun getProfitLossText(change: Float) = if (change >= 0) resProvider.getString(R.string.profit) else resProvider.getString(R.string.loss)

    private fun addCoinsChangesObservable() {
        disposable.add(db.coinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinsFromDbUpdates(it) }))
    }

    private fun onCoinsFromDbUpdates(list: List<Coin>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
            sortCoinsBySelectedSortMethod()
            view.disableEmptyText()
            view.updateRecyclerView()
            if (isFirstStart) {
                isFirstStart = false
                updatePrices()
            }
            updateHoldings()
        } else {
            coins.clear()
            view.enableEmptyText()
            view.updateRecyclerView()
        }
    }

    private fun updatePrices() {
        val queryMap = createCoinsMapWithCurrencies(coins)
        if (queryMap.isNotEmpty()) {
            RxBus.publish(CoinsLoadingEvent(true))
            disposable.add(networkRequests.getPrice(queryMap)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onPriceUpdated(it) }, { afterRefreshing() }))
        }
    }

    private fun onPriceUpdated(list: ArrayList<Coin>) {
        if (list.isNotEmpty()) coinsController.saveCoinsList(filterList(list))
        afterRefreshing()
    }

    private fun filterList(coinsInfoList: ArrayList<Coin>): ArrayList<Coin> {
        val result: ArrayList<Coin> = ArrayList()
        coins.forEach { (from, to) ->
            val find = coinsInfoList.find { it.from == from && it.to == to }
            if (find != null) result.add(find)
        }
        return result
    }

    private fun afterRefreshing() {
        RxBus.publish(CoinsLoadingEvent(false))
        if (isRefreshing) {
            view.hideRefreshing()
            isRefreshing = false
            view.enableSwipeToRefresh()
        }
    }

    override fun onStop() {
        disposable.clear()
        disableSelected()
        RxBus.publish(CoinsLoadingEvent(false))
    }

    override fun onSwipeUpdate() {
        disableSelected()
        isRefreshing = true
        updatePrices()
    }

    override fun onCoinClicked(coin: Coin) {
        view.startCoinInfoActivity(coin.from, coin.to)
    }

    override fun onHoldingsClicked() {
        view.startHoldingsActivity()
    }

    override fun onAllocationsClicked() {
        view.startAllocationsActivity()
    }
}