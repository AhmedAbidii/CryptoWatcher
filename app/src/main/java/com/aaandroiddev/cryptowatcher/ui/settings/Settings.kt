package com.aaandroiddev.cryptowatcher.ui.settings

interface Settings {

    interface View {
        fun setLanguage(language: String)
        fun showLanguageDialog()
    }

    interface Presenter {
        fun onCreate()
        fun onLanguageClicked()
        fun onStop()
    }

}