package com.aaandroiddev.cryptowatcher

import android.content.Context
import com.aaandroiddev.cryptowatcher.model.LocaleManager
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }

}