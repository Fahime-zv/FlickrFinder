
package com.fahimezv.flickrfindr.presentaion.common.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecorations {

    class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.top = spacing / 2
            outRect.left = spacing / 2
            outRect.right = spacing / 2
            outRect.bottom = spacing / 2
        }

    }

}