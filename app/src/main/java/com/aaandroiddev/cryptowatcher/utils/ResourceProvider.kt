package com.aaandroiddev.cryptowatcher.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

class ResourceProvider(val context: Context) {

    fun getString(id: Int): String = context.getString(id)
    fun getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(context, id)
    fun getColor(id: Int) = ContextCompat.getColor(context, id)
    fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)
}