package com.aaandroiddev.cryptowatcher.ui.addTransaction

import android.content.Context
import android.os.Bundle
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.DEFAULT_DATE_FORMAT
import com.aaandroiddev.cryptowatcher.model.FROM
import com.aaandroiddev.cryptowatcher.model.PRICE
import com.aaandroiddev.cryptowatcher.model.TO
import com.aaandroiddev.cryptowatcher.model.classes.HoldingData
import com.aaandroiddev.cryptowatcher.model.database.DBController
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import com.aaandroiddev.cryptowatcher.utils.formatLongDateToString
import com.aaandroiddev.cryptowatcher.utils.toastShort
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class AddTransactionPresenter @Inject constructor(private val view: IAddTransaction.View,
                                                  private val context: Context,
                                                  private val resourceProvider: ResourceProvider,
                                                  private val dbController: DBController) : IAddTransaction.Presenter{
    private var from = ""
    private var to = ""
    private var price = 0.0
    private var transactionDate: Date? = null
    private val disposable = CompositeDisposable()
    private var tradingPrice: Float = 0f
        set(value) {
            field = value
            calculateTotalValue()
        }
    private var quantity: Float = 0f
        set(value) {
            field = value
            calculateTotalValue()
        }

    private fun calculateTotalValue() {
        if (tradingPrice != 0f && quantity != 0f) {
            view.setTotalValue((tradingPrice * quantity).toString())
        }
    }

    override fun onCreate(extras: Bundle) {
        if (extras[FROM] != null && extras[TO] != null && extras[PRICE] != null) {
            from = extras.getString(FROM)
            to = extras.getString(TO)
            price = extras.getString(PRICE).substring(2).replace(",", "").toDouble()
            view.setTitle("$from / $to", extras.getString(PRICE))
        }
    }

    override fun observeTradingPrice(observable: Observable<CharSequence>) {
        disposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTradingPriceTextChanged))
    }

    private fun onTradingPriceTextChanged(char: CharSequence) {
        val text = char.toString()
        if (text.isNotEmpty() && text != ".") tradingPrice = text.toFloat()
        else {
            tradingPrice = 0f
            view.setTotalValue("0.0")
        }
    }

    override fun observeQuantity(observable: Observable<CharSequence>) {
        disposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onQuantityTextChanged))
    }

    private fun onQuantityTextChanged(char: CharSequence) {
        val text = char.toString()
        if (text.isNotEmpty() && text != ".") quantity = text.toFloat()
        else {
            quantity = 0f
            view.setTotalValue("0.0")
        }
    }

    override fun observeDateClicks(observable: Observable<Unit>) {
        disposable.add(observable
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.showDatePickerDialog() }))
    }

    override fun observeConfirmClicks(observable: Observable<Unit>) {
        disposable.add(observable
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onConfirmClicked() }))
    }

    override fun onDatePicked(year: Int, month: Int, day: Int) {
        val pickedDate = Calendar.getInstance()
        pickedDate.set(Calendar.YEAR, year)
        pickedDate.set(Calendar.MONTH, month)
        pickedDate.set(Calendar.DATE, day)
        transactionDate = pickedDate.time
        setDate()
    }

    private fun setDate() {
        view.setTransactionDate(formatLongDateToString(transactionDate?.time, DEFAULT_DATE_FORMAT))
    }

    override fun onDatePickerDialogCancelled() {
        transactionDate = null
    }

    private fun onConfirmClicked() {
        if (allFieldsAreFilled()) {
            saveHoldings()
        } else if (tradingPrice == 0f && quantity == 0f && transactionDate == null) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_all_fields))
        } else if (transactionDate == null) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_date))
        } else if (tradingPrice == 0f) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_price))
        } else {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_quantity))
        }
    }

    private fun allFieldsAreFilled() = tradingPrice != 0f && quantity != 0f && transactionDate != null

    private fun saveHoldings() {
        dbController.saveHoldingData(HoldingData(from, to, quantity, tradingPrice, transactionDate!!.time))
        context.toastShort(resourceProvider.getString(R.string.transaction_added))
        view.closeActivity()
    }

    override fun onStop() {
        disposable.clear()
    }
}