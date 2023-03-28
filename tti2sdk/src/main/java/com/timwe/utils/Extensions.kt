package com.timwe.utils

import android.view.View
import android.view.ViewTreeObserver

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

