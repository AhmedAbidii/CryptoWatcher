package com.aaandroiddev.cryptowatcher.ui.topCoins

import android.content.Context
import android.util.Log
import android.view.View
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.CoinsController
import com.aaandroiddev.cryptowatcher.model.PageController
import com.aaandroiddev.cryptowatcher.model.TOP_COINS_FRAGMENT_PAGE_POSITION
import com.aaandroiddev.cryptowatcher.model.USD
import com.aaandroiddev.cryptowatcher.model.classes.Coin
import com.aaandroiddev.cryptowatcher.model.classes.InfoCoin
import com.aaandroiddev.cryptowatcher.model.classes.TopCoinData
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.model.network.NetworkRequests
import com.aaandroiddev.cryptowatcher.model.rxbus.MainCoinsListUpdatedEvent
import com.aaandroiddev.cryptowatcher.model.rxbus.RxBus
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import com.aaandroiddev.cryptowatcher.utils.createCoinsMapWithCurrencies
import com.aaandroiddev.cryptowatcher.utils.logDebug
import com.aaandroiddev.cryptowatcher.utils.toastShort
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.top_coin_item.view.*
import javax.inject.Inject


class TopCoinsPresenter @Inject constructor(private val context: Context,
                                            private val view: ITopCoins.View,
                                            private val db: CryptoWatcherDatabase,
                                            private val networkRequests: NetworkRequests,
                                            private val coinsController: CoinsController,
                                            private val resProvider: ResourceProvider,
                                            private val pageController: PageController) : ITopCoins.Presenter {
    private val disposable = CompositeDisposable()
    private var coins: ArrayList<TopCoinData> = ArrayList()
    private var isRefreshing = false
    private var needToUpdate = false

    override fun onCreate(coins: ArrayList<TopCoinData>) {
        this.coins = coins
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        disposable.add(db.topCoinsDao().getAllTopCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinsUpdated(it) }))
        disposable.add(db.allCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onAllCoinsUpdated(it) }))
        disposable.add(RxBus.listen(MainCoinsListUpdatedEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onMainCoinsUpdated() })
        addOnPageChangedObservable()
    }

    private fun onCoinsUpdated(list: List<TopCoinData>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
            coins.sortBy { it.rank }
            view.updateRecyclerView()
        }
    }

    private fun onAllCoinsUpdated(list: List<InfoCoin>) {
        if (list.isNotEmpty()) {
            updateTopCoins()
        }
    }

    private fun onMainCoinsUpdated() {
        needToUpdate = true
    }

    private fun addOnPageChangedObservable() {
        disposable.add(pageController.getPageObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onPageChanged(it) })
    }

    private fun onPageChanged(position: Int) {
        if (position == TOP_COINS_FRAGMENT_PAGE_POSITION && needToUpdate) {
            view.updateRecyclerView()
            needToUpdate = false
        }
    }

    override fun onStart() {
        updateTopCoins()
        updateAllCoins()
    }

    override fun onStop() {
        disposable.clear()
    }

    private fun updateTopCoins() {
        disposable.add(networkRequests.getTopCoins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onTopCoinsReceived(it) },
                        { Log.e("updateTopCoins", it.toString()) }))
    }

    private fun onTopCoinsReceived(coins: List<TopCoinData>) {
        if (coins.isNotEmpty()) {
            coinsController.saveTopCoinsList(coins)
            if (isRefreshing) {
                view.hideRefreshing()
                isRefreshing = false
            }
        }
    }

    private fun updateAllCoins() {
        if (coinsController.allInfoCoinsIsEmpty()) {
            disposable.add(networkRequests.getAllCoins()
                    .subscribe({ onAllCoinsReceived(it) },
                            { Log.e("getAllCoinsInfo", it.toString()) }))
        } else {
            updateTopCoins()
        }
    }

    private fun onAllCoinsReceived(list: ArrayList<InfoCoin>) {
        if (list.isNotEmpty()) {
            coinsController.saveAllCoinsInfo(list)
        }
    }

    override fun onCoinClicked(coin: TopCoinData) {
        view.startCoinInfoActivity(coin.symbol)
    }

    override fun onSwipeUpdate() {
        isRefreshing = true
        updateTopCoins()
    }

    override fun onAddCoinClicked(coin: TopCoinData, itemView: View) {
        itemView.top_coin_add_loading.visibility = View.VISIBLE
        itemView.top_coin_add_icon.visibility = View.GONE
        val coinFrom = Coin(from = coin.symbol!!, to = USD)
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coinFrom)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinAdded(it, itemView) }, { onError(itemView) }))
    }

    private fun onCoinAdded(list: ArrayList<Coin>, itemView: View) {
        if (list.isNotEmpty()) {
            coinsController.saveCoinsList(list)
            itemView.top_coin_add_icon.setImageDrawable(resProvider.getDrawable(R.drawable.ic_done))
            context.toastShort(resProvider.getString(R.string.coin_added))
        } else {
            context.toastShort(resProvider.getString(R.string.error))
        }
        afterAdded(itemView)
    }

    private fun onError(itemView: View) {
        afterAdded(itemView)
        context.toastShort(resProvider.getString(R.string.error))
    }

    private fun afterAdded(itemView: View) {
        itemView.top_coin_add_loading.visibility = View.GONE
        itemView.top_coin_add_icon.visibility = View.VISIBLE
    }
}