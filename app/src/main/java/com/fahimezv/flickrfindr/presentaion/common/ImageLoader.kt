package com.fahimezv.flickrfindr.presentaion.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.fahimezv.flickrfindr.R
import com.squareup.picasso.Picasso

class ImageLoader private constructor(private val context: Context) {
    private var resource: String? = null
    private var imageView: ImageView? = null
    private var placeholderDrawable: Drawable? = null

    fun load(res: String): ImageLoader {
        this.resource = res
        return this
    }

    fun placeholder(placeholder: Drawable): ImageLoader {
        placeholderDrawable = placeholder
        return this
    }

    fun into(imageView: ImageView) {
        this.imageView = imageView
        build()
    }


    private fun build() {
        val picasso = Picasso.get()
        picasso.load(resource).placeholder(R.drawable.noimage).into(imageView)
    }

    companion object {
        fun with(context: Context): ImageLoader {
            return ImageLoader(context)
        }
    }
}