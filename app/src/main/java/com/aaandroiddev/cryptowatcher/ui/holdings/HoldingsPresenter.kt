package com.aaandroiddev.cryptowatcher.ui.holdings

import android.content.Context
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.classes.HoldingData
import com.aaandroiddev.cryptowatcher.model.database.CryptoWatcherDatabase
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import com.aaandroiddev.cryptowatcher.utils.toastShort
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HoldingsPresenter @Inject constructor(private val view: IHoldings.View,
                                            private val db: CryptoWatcherDatabase,
                                            private val context: Context,
                                            private val resourceProvider: ResourceProvider) : IHoldings.Presenter {

    private val disposable = CompositeDisposable()
    private var holdings: ArrayList<HoldingData> = ArrayList()

    override fun onCreate(holdings: ArrayList<HoldingData>) {
        this.holdings = holdings
        addHoldingsChangesObservable()
    }

    private fun addHoldingsChangesObservable() {
        disposable.add(db.holdingsDao().getAllHoldings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onHoldingsUpdate(it) }))
    }

    private fun onHoldingsUpdate(list: List<HoldingData>) {
        if (list.isNotEmpty()) {
            holdings.clear()
            holdings.addAll(list)
            view.updateRecyclerView()
        }
    }

    override fun onStop() {
        disposable.clear()
    }

    override fun onItemSwiped(position: Int?) {
        if (position != null) {
            disposable.add(Single.fromCallable { db.holdingsDao().deleteHolding(holdings[position]) }
                    .subscribeOn(Schedulers.io())
                    .subscribe())
            context.toastShort(resourceProvider.getString(R.string.holdings_deleted))
        }
    }
}