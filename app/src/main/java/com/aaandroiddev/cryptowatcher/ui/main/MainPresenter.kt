package com.aaandroiddev.cryptowatcher.ui.main

import com.aaandroiddev.cryptowatcher.model.COINS_FRAGMENT_PAGE_POSITION
import com.aaandroiddev.cryptowatcher.model.MultiSelector
import com.aaandroiddev.cryptowatcher.model.PageController
import com.aaandroiddev.cryptowatcher.model.rxbus.CoinsLoadingEvent
import com.aaandroiddev.cryptowatcher.model.rxbus.OnDeleteCoinsMenuItemClickedEvent
import com.aaandroiddev.cryptowatcher.model.rxbus.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainPresenter @Inject constructor(private val view: IMain.View,
                                        private val multiSelector: MultiSelector,
                                        private val pageController: PageController) : IMain.Presenter {
    private val disposable = CompositeDisposable()

    override fun onCreate() {
        setObservers()
    }

    private fun setObservers() {
        disposable.add(RxBus.listen(CoinsLoadingEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setCoinsLoadingVisibility(it.isLoading)
                })
        disposable.add(multiSelector.getSelectorObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setMenuIconsVisibility(it)
                })
        disposable.add(pageController.getPageObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onPageChanged(it) })
    }

    private fun onPageChanged(position: Int) {
        view.setSortVisible(position == COINS_FRAGMENT_PAGE_POSITION)
    }

    override fun onStop() {
        disposable.clear()
    }

    override fun onAddCoinClicked() {
        view.startAddCoinActivity()
    }

    override fun onSortClicked() {
        view.showCoinsSortDialog()
    }

    override fun onSettingsClicked() {
        view.openSettings()
    }

    override fun onDeleteClicked() {
        view.setMenuIconsVisibility(false)
        RxBus.publish(OnDeleteCoinsMenuItemClickedEvent())
    }

    override fun onPageSelected(position: Int) {
        pageController.pageSelected(position)
    }
}