package com.aaandroiddev.cryptowatcher.ui.coins


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.HoldingsHandler
import com.aaandroiddev.cryptowatcher.model.MultiSelector
import com.aaandroiddev.cryptowatcher.model.NAME
import com.aaandroiddev.cryptowatcher.model.TO
import com.aaandroiddev.cryptowatcher.model.classes.Coin
import com.aaandroiddev.cryptowatcher.ui.base.BaseFragment
import com.aaandroiddev.cryptowatcher.ui.coinAllocation.CoinAllocationActivity
import com.aaandroiddev.cryptowatcher.ui.coinInfo.CoinInfoActivity
import com.aaandroiddev.cryptowatcher.ui.holdings.HoldingsActivity
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_coins.*
import javax.inject.Inject


class CoinsFragment : BaseFragment(),ICoins.View {

    @Inject lateinit var presenter: ICoins.Presenter
    @Inject lateinit var resProvider: ResourceProvider
    @Inject lateinit var multiSelector: MultiSelector
    @Inject lateinit var holdingsHandler: HoldingsHandler

    private lateinit var recView: RecyclerView
    private lateinit var adapter: CoinsListAdapter
    private var coins: ArrayList<Coin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(coins)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_coins, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        setupSwipeRefresh()
        presenter.onViewCreated()
        coins_fragment_holding_layout.setOnClickListener { presenter.onHoldingsClicked() }
        coins_fragment_allocations.setOnClickListener { presenter.onAllocationsClicked() }
    }

    private fun setupRecView() {
        recView = coins_fragment_rec_view
        recView.layoutManager = LinearLayoutManager(activity)
        adapter = CoinsListAdapter(coins, resProvider, multiSelector, holdingsHandler,
                clickListener = { presenter.onCoinClicked(it) })
        recView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        swipe_refresh.setOnRefreshListener { presenter.onSwipeUpdate() }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun updateRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    override fun hideRefreshing() {
        swipe_refresh.isRefreshing = false
    }

    override fun enableSwipeToRefresh() {
        swipe_refresh.isEnabled = true
    }

    override fun disableSwipeToRefresh() {
        swipe_refresh.isEnabled = false
    }

    override fun startCoinInfoActivity(name: String?, to: String?) {
        val intent = Intent(context, CoinInfoActivity::class.java)
        intent.putExtra(NAME, name)
        intent.putExtra(TO, to)
        activity?.startActivity(intent)
    }

    override fun startAllocationsActivity() {
        activity?.startActivity(Intent(context, CoinAllocationActivity::class.java))
    }

    override fun enableTotalHoldings() {
        coins_fragment_holding_layout.visibility = View.VISIBLE
        coins_fragment_holdings_line.visibility = View.VISIBLE
    }

    override fun disableTotalHoldings() {
        coins_fragment_holding_layout.visibility = View.GONE
        coins_fragment_holdings_line.visibility = View.GONE
    }

    override fun setTotalHoldingsValue(total: String) {
        coins_fragment_total_holdings.text = total
    }

    override fun setTotalHoldingsChangePercent(percent: String) {
        coins_fragment_holdings_change_percent.text = percent
    }

    override fun setTotalHoldingsChangePercentColor(color: Int) {
        coins_fragment_holdings_change_percent.setTextColor(resProvider.getColor(color))
    }

    override fun startHoldingsActivity() {
        startActivity(Intent(activity, HoldingsActivity::class.java))
    }

    override fun setTotalHoldingsChangeValue(value: String) {
        coins_fragment_holdings_change_value.text = value
    }

    override fun setTotalHoldingsChangeValueColor(color: Int) {
        coins_fragment_holdings_change_value.setTextColor(resProvider.getColor(color))
    }

    override fun setAllTimeProfitLossString(text: String) {
        coins_fragment_all_time_profit_loss.text = text
    }

    override fun enableEmptyText() {
        coins_fragment_empty_text.visibility = View.VISIBLE
    }

    override fun disableEmptyText() {
        coins_fragment_empty_text.visibility = View.GONE
    }
}
