package com.aaandroiddev.cryptowatcher.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toastShort(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.logDebug(message: String) {
    Log.d("debug", message)
}

fun Context.logError(message: String) {
    Log.e("error", message)
}
