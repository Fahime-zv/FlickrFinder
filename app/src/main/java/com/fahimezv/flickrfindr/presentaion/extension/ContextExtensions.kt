package com.fahimezv.flickrfindr.presentaion.extension

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(windowToken: IBinder) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}