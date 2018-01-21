package com.aaandroiddev.cryptowatcher.ui.holdings

import com.aaandroiddev.cryptowatcher.model.classes.HoldingData

interface IHoldings {
    interface View {
        fun updateRecyclerView()
    }
    interface Presenter {
        fun onCreate(holdings: ArrayList<HoldingData>)
        fun onItemSwiped(position: Int?)
        fun onStop()
    }
}