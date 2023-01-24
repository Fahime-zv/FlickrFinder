
package com.fahimezv.flickrfindr.presentaion.common.extentions

import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import androidx.fragment.app.Fragment

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

@Suppress("DEPRECATION")
inline val Fragment.screenWidth: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val displayMetrics = DisplayMetrics()
            val display: Display? = requireContext().display
            display!!.getRealMetrics(displayMetrics)
            displayMetrics.widthPixels
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }