package com.fahimezv.flickrfindr.presentaion.extension

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.doOnSearch(listener: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            listener.invoke(text.toString())
        }
        false
    }
}