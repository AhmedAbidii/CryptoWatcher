package com.aaandroiddev.cryptowatcher.ui.coinAllocation

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.ui.base.BaseActivity
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_coin_allocation.*
import javax.inject.Inject

class CoinAllocationActivity : BaseActivity(), ICoinAllocation.View {

    @Inject lateinit var presenter: ICoinAllocation.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_allocation)
        setupToolbar()
        presenter.onCreate()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resProvider.getString(R.string.allocations)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun drawPieChart(pieData: PieData) {
        with(coin_allocation_pie) {
            data = pieData
            description = Description().apply { text = "Coin % of Holdings" }
            setHoleColor(Color.TRANSPARENT)
            setUsePercentValues(true)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun enableGraphLoading() {
        coin_allocation_loading.visibility = View.VISIBLE
        coin_allocation_pie.visibility = View.GONE
    }

    override fun disableGraphLoading() {
        coin_allocation_loading.visibility = View.GONE
        coin_allocation_pie.visibility = View.VISIBLE
    }
}
