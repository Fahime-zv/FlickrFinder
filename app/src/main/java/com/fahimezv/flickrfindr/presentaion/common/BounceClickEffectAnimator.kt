package com.fahimezv.flickrfindr.presentaion.common


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
class BounceClickEffectAnimator(
    view: View,
    private val scaleOffSet: Float = 0.03F,
    private val alphaScale: Float = 0.8F
) {

    private val onTouchActionDownAnimatorSet: AnimatorSet = AnimatorSet().apply {
        val xScale = ObjectAnimator.ofFloat(view, "scaleX", (1 - scaleOffSet))
        val yScale = ObjectAnimator.ofFloat(view, "scaleY", (1 - scaleOffSet))
        val alpha = ObjectAnimator.ofFloat(view, "alpha", alphaScale)
        playTogether(xScale, yScale, alpha)
    }

    private val onTouchActionUpAnimatorSet: AnimatorSet = AnimatorSet().apply {
        val xScale = ObjectAnimator.ofFloat(
            view,
            "scaleX",
            (1 - scaleOffSet),
            (1 + scaleOffSet),
            1F
        )
        val yScale = ObjectAnimator.ofFloat(
            view,
            "scaleY",
            (1 - scaleOffSet),
            (1 + scaleOffSet),
            1F
        )
        val alpha = ObjectAnimator.ofFloat(view, "alpha", alphaScale, 1F)
        playTogether(xScale, yScale, alpha)
    }

    init {
        view.setOnTouchListener { _, motionEvent ->
            if (view.isEnabled && view.isClickable) {
                when (motionEvent?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        touchEffect()
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        releaseEffect()
                    }
                }
            }
            view.onTouchEvent(motionEvent)
        }
    }

    private fun touchEffect() {
        onTouchActionUpAnimatorSet.end()
        onTouchActionDownAnimatorSet.start()
    }

    private fun releaseEffect() {
        onTouchActionDownAnimatorSet.end()
        onTouchActionUpAnimatorSet.start()
    }
}
