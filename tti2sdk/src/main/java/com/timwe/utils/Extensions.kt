package com.timwe.utils

import android.graphics.Bitmap
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat

inline fun View.onDebouncedListener(
    delayInClick: Long = 500L,
    crossinline listener: (View) -> Unit){

    val enableAgain = Runnable { isEnabled = true }

    setOnClickListener{
        if(isEnabled){
            isEnabled = false
            postDelayed(enableAgain, delayInClick)
            listener(it)
        }
    }
}

inline fun View.getDimensions(crossinline onDimensionsReady: (Int, Int) -> Unit) {
    lateinit var layoutListener: ViewTreeObserver.OnGlobalLayoutListener
    layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
        onDimensionsReady(width, height)
    }
    viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
}

fun View.drawToBitmap(config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
    if (!ViewCompat.isLaidOut(this)) {
        throw IllegalStateException("View needs to be laid out before calling drawToBitmap()")
    }

    val bitmap =  Bitmap.createBitmap(width, height, config).applyCanvas {
        translate(-scrollX.toFloat(), -scrollY.toFloat())
        draw(this)
    }
    return bitmap
}

