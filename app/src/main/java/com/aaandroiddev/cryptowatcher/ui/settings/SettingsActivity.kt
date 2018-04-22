package com.aaandroiddev.cryptowatcher.ui.settings

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.aaandroiddev.cryptowatcher.ui.base.BaseActivity
import com.aaandroiddev.cryptowatcher.R
import com.aaandroiddev.cryptowatcher.utils.ResourceProvider
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : BaseActivity(), Settings.View {

    @Inject lateinit var presenter: Settings.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupToolbar()
        presenter.onCreate()
        language_layout.setOnClickListener { presenter.onLanguageClicked() }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resProvider.getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun setLanguage(language: String) {
        settings_language.text = language
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun showLanguageDialog() {
        val dialog = LanguageDialog()
        dialog.show(supportFragmentManager, "languageDialog")
    }
}
