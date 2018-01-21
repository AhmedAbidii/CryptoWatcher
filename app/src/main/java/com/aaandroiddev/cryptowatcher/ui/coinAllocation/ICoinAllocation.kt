package com.aaandroiddev.cryptowatcher.ui.coinAllocation

import com.github.mikephil.charting.data.PieData

interface ICoinAllocation {
    interface View {
        fun drawPieChart(pieData: PieData)
        fun enableGraphLoading()
        fun disableGraphLoading()
    }

    interface Presenter {
        fun onCreate()
        fun onStop()
    }
}