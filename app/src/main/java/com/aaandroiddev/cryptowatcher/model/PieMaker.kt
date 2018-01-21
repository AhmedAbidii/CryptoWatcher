package com.aaandroiddev.cryptowatcher.model

import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.classes.Coin
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS


class PieMaker(val resProvider: ResourceProvider,
               val holdingsHandler: HoldingsHandler) {

    companion object {
        private val TEXT_SIZE_DP: Float = 17f
    }

    fun makeChart(coinList: ArrayList<Coin>): PieData {
        val pieEntryList: ArrayList<PieEntry> = ArrayList()
        coinList.forEach { setupPieEntry(it, pieEntryList) }

        val dataSet = PieDataSet(pieEntryList.toList(), resProvider.getString(R.string.coins))
                .also { setupDataSetParams(it) }

        return PieData(dataSet)
                .also { setupPieData(it) }
    }

    private fun setupPieEntry(coin: Coin, pieEntryList: ArrayList<PieEntry>) {
        val holding = holdingsHandler.isThereSuchHolding(coin.from, coin.to)
        if (holding != null) {
            val value = holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holding)
            pieEntryList.add(PieEntry(value, coin.from))
        } else {
            println("No holding for $coin")
        }
    }

    private fun setupPieData(pieData: PieData) {
        with(pieData) {
            setValueTextSize(TEXT_SIZE_DP)
            setValueFormatter(PercentFormatter())
        }
    }

    private fun setupDataSetParams(dataSet: PieDataSet) {
        with(dataSet) {
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            colors = MATERIAL_COLORS.asList()
        }
    }
}