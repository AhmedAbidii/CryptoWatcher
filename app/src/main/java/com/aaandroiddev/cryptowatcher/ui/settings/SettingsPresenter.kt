package com.aaandroiddev.cryptowatcher.ui.settings

import android.content.Context
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.LocaleManager
import com.aaandroiddev.cryptowatcher.model.Preferences
import com.aaandroiddev.cryptowatcher.model.rxbus.LanguageChanged
import com.aaandroiddev.cryptowatcher.model.rxbus.RxBus
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        private val view: Settings.View,
        private val context: Context,
        private val resourceProvider: ResourceProvider) : Settings.Presenter {

    private val prefs = Preferences(context)
    private val disposable = CompositeDisposable()

    override fun onCreate() {
        initLanguage()
        setRxEventsListeners()
    }

    private fun initLanguage() {
        if (prefs.language.isEmpty()) prefs.language = LocaleManager.getLocale(context.resources).language
        view.setLanguage(getLanguageFromPrefs())
    }

    private fun getLanguageFromPrefs() = when (prefs.language) {
        LocaleManager.FRENCH -> resourceProvider.getString(R.string.french)
        else -> resourceProvider.getString(R.string.english)
    }

    private fun setRxEventsListeners() {
        disposable.add(RxBus.listen(LanguageChanged::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onLanguageChanged() })
    }

    private fun onLanguageChanged() {
        LocaleManager.setNewLocale(context, prefs.language)
        System.exit(0)
    }

    override fun onLanguageClicked() {
        view.showLanguageDialog()
    }

    override fun onStop() {
        disposable.clear()
    }
}