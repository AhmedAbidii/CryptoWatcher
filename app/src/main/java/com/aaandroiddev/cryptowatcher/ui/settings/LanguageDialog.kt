package com.aaandroiddev.cryptowatcher.ui.settings

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.model.LocaleManager
import com.aaandroiddev.cryptowatcher.model.Preferences
import com.aaandroiddev.cryptowatcher.model.rxbus.LanguageChanged
import com.aaandroiddev.cryptowatcher.model.rxbus.RxBus
import kotlinx.android.synthetic.main.language_dialog.*

class LanguageDialog : DialogFragment() {

    private lateinit var prefs: Preferences
    private lateinit var selectedLang: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Preferences(activity?.applicationContext!!)
        selectedLang = prefs.language
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.language_dialog, container, false)!!

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lang_dialog_cancel.setOnClickListener { dismiss() }
        setCheckedBtn()
        radio_group.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.lang_dialog_english -> onLanguageSelected(LocaleManager.ENGLISH)
                R.id.lang_dialog_russian -> onLanguageSelected(LocaleManager.FRENCH)
            }
        }
    }

    private fun setCheckedBtn() {
        when (prefs.language) {
            LocaleManager.ENGLISH -> lang_dialog_english.isChecked = true
            LocaleManager.FRENCH -> lang_dialog_russian.isChecked = true
        }
    }

    private fun onLanguageSelected(language: String) {
        prefs.language = language
        RxBus.publish(LanguageChanged())
        dismiss()
    }

}