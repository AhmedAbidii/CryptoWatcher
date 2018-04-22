package com.aaandroiddev.cryptowatcher.ui.base

import android.content.Context
import com.aaandroiddev.cryptowatcher.model.LocaleManager
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }

}