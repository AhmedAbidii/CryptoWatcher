package com.aaandroiddev.cryptowatcher.model

import android.graphics.Paint
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.classes.HistoData
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry


class GraphMaker(val resProvider: ResourceProvider) {

    fun makeChart(histoList: ArrayList<HistoData>, period: String): CandleData {
        val candleList: ArrayList<CandleEntry> = ArrayList()
        var x = 0f
        histoList.forEach {
            candleList.add(CandleEntry(++x, it.high, it.low, it.open, it.close))
        }
        val dataSet = CandleDataSet(candleList.toList(), period)
        setupDataSetParams(dataSet)
        return CandleData(dataSet)
    }

    private fun setupDataSetParams(dataSet: CandleDataSet) {
        dataSet.shadowColor = resProvider.getColor(R.color.grey)
        dataSet.shadowWidth = 0.7f
        dataSet.decreasingColor = resProvider.getColor(R.color.red)
        dataSet.decreasingPaintStyle = Paint.Style.FILL
        dataSet.increasingColor = resProvider.getColor(R.color.green)
        dataSet.increasingPaintStyle = Paint.Style.FILL
        dataSet.neutralColor = resProvider.getColor(R.color.orange_dark)
        dataSet.setDrawValues(false)
    }
}