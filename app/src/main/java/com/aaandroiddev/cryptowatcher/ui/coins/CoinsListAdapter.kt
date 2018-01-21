package com.aaandroiddev.cryptowatcher.ui.coins

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.HoldingsHandler
import com.aaandroiddev.cryptowatcher.model.MultiSelector
import com.aaandroiddev.cryptowatcher.model.classes.Coin
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import com.aaandroiddev.cryptowatcher.utils.getChangeColor
import com.aaandroiddev.cryptowatcher.utils.getStringWithTwoDecimalsFromDouble
import kotlinx.android.synthetic.main.coins_list_item.view.*

class CoinsListAdapter(private val coins: ArrayList<Coin>,
                       private val resProvider: ResourceProvider,
                       private val multiSelector: MultiSelector,
                       private val holdingsHandler: HoldingsHandler,
                       val clickListener: (Coin) -> Unit) : RecyclerView.Adapter<CoinsListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(coins[position], clickListener)
    }

    private fun getChangeArrowDrawable(change: Float) = when {
        change > 0 -> R.drawable.ic_arrow_drop_up_green
        change == 0f -> R.drawable.ic_remove_orange
        else -> R.drawable.ic_arrow_drop_down_red
    }

    override fun getItemCount() = coins.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.coins_list_item, parent, false))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(coin: Coin, listener: (Coin) -> Unit) = with(itemView) {
            setOnClickListener {
                if (multiSelector.atLeastOneIsSelected) {
                    multiSelector.onClick(coin, main_item_layout, coins)
                } else {
                    listener(coin)
                }
            }
            setOnLongClickListener {
                multiSelector.onClick(coin, main_item_layout, coins)
            }
            if (coin.selected) {
                main_item_layout.setBackgroundColor(resProvider.getColor(R.color.colorAccent))
            } else {
                main_item_layout.setBackgroundResource(0)
            }
            main_item_from.text = coin.from
            val to = " / ${coin.to}"
            main_item_to.text = to
            main_item_full_name.text = coin.fullName
            main_item_last_price.text = coin.price
            val chPct24h = "${coin.changePct24h}%"
            main_item_change_in_24.text = chPct24h
            main_item_change_in_24.setTextColor(resProvider.getColor(getChangeColor(coin.changePct24hRaw)))
            main_item_price_arrow.setImageDrawable(resProvider.getDrawable(getChangeArrowDrawable(coin.changePct24hRaw)))
            if (coin.imgUrl.isNotEmpty()) {
                com.squareup.picasso.Picasso.with(context)
                        .load(coin.imgUrl)
                        .into(main_item_market_logo)
            }

            val holding = holdingsHandler.isThereSuchHolding(coin.from, coin.to)
            if (holding != null) {
                main_item_holding_qty.text = getStringWithTwoDecimalsFromDouble(holding.quantity)
                val value = "$${getStringWithTwoDecimalsFromDouble(holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holding))}"
                main_item_holding_value.text = value
                main_item_holding_qty.visibility = android.view.View.VISIBLE
                main_item_holding_value.visibility = android.view.View.VISIBLE
            } else {
                main_item_holding_qty.visibility = android.view.View.GONE
                main_item_holding_value.visibility = android.view.View.GONE
            }
        }
    }
}