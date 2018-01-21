package com.aaandroiddev.cryptowatcher.ui.topCoins


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.CoinsController
import com.aaandroiddev.cryptowatcher.model.NAME
import com.aaandroiddev.cryptowatcher.model.TO
import com.aaandroiddev.cryptowatcher.model.USD
import com.aaandroiddev.cryptowatcher.model.classes.TopCoinData
import com.aaandroiddev.cryptowatcher.ui.coinInfo.CoinInfoActivity
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_top_coins.*
import javax.inject.Inject

class TopCoinsFragment : DaggerFragment(), ITopCoins.View {

    @Inject lateinit var presenter: ITopCoins.Presenter
    @Inject lateinit var resProvider: ResourceProvider
    @Inject lateinit var coinsController: CoinsController

    private lateinit var recView: RecyclerView
    private lateinit var adapter: TopCoinsAdapter
    private var coins: ArrayList<TopCoinData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(coins)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_top_coins, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        setupSwipeRefresh()
    }

    private fun setupRecView() {
        recView = top_coins_fragment_rec_view
        recView.layoutManager = LinearLayoutManager(activity)
        adapter = TopCoinsAdapter(coins, resProvider, presenter, coinsController,
                clickListener = { presenter.onCoinClicked(it) })
        recView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        top_coins_fragment_swipe_refresh.setOnRefreshListener {
            presenter.onSwipeUpdate()
        }
    }

    override fun updateRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun hideRefreshing() {
        top_coins_fragment_swipe_refresh.isRefreshing = false
    }

    override fun startCoinInfoActivity(name: String?) {
        val intent = Intent(context, CoinInfoActivity::class.java)
        intent.putExtra(NAME, name)
        intent.putExtra(TO, USD)
        activity?.startActivity(intent)
    }

}