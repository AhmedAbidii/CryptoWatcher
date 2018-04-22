package com.aaandroiddev.cryptowatcher.ui.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ABIDI Ahmed on 22/04/2018.
 */

abstract class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    protected fun getCompositeDisposable(): CompositeDisposable? {
        return if (compositeDisposable != null) {
            compositeDisposable
        } else {
            CompositeDisposable()
        }
    }

    override fun onCleared() {
        super.onCleared()

        if (compositeDisposable != null && !compositeDisposable!!.isDisposed)
            compositeDisposable!!.dispose()
        compositeDisposable = null
    }
}