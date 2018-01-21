package com.aaandroiddev.cryptowatcher.ui.main

interface IMain {

    interface View {
        fun setCoinsLoadingVisibility(isLoading: Boolean)
        fun startAddCoinActivity()
        fun setMenuIconsVisibility(isSelected: Boolean)
        fun showToast(text: String)
        fun setSortVisible(isVisible: Boolean)
        fun showCoinsSortDialog()
        fun openSettings()
    }

    interface Presenter {
        fun onCreate()
        fun onAddCoinClicked()
        fun onSettingsClicked()
        fun onDeleteClicked()
        fun onPageSelected(position: Int)
        fun onSortClicked()
        fun onStop()
    }
}