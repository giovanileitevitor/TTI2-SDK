package com.timwe.utils

import android.graphics.Bitmap
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import java.util.*


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


inline fun View.drawToBitmap(config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
    if (!ViewCompat.isLaidOut(this)) {
        throw IllegalStateException("View needs to be laid out before calling drawToBitmap()")
    }

    return  Bitmap.createBitmap(width, height, config).applyCanvas {
        translate(-scrollX.toFloat(), -scrollY.toFloat())
        draw(this)
    }
}

inline fun Long.toDateString(): String{

    val timestamp: Long = this

    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = timestamp

    val day: Int = cal.get(Calendar.DAY_OF_MONTH)
    val month: Int = cal.get(Calendar.MONTH)
    var monthAux = ""

    when(month){
        0 -> monthAux = "JANUARY"
        1 -> monthAux = "FEBRUARY"
        2 -> monthAux = "MARCH"
        3 -> monthAux = "APRIL"
        4 -> monthAux = "MAY"
        5 -> monthAux = "JUNE"
        6 -> monthAux = "JULY"
        7 -> monthAux = "AUGUST"
        8 -> monthAux = "SEPTEMBER"
        9 -> monthAux = "OCTOBER"
        10 -> monthAux = "NOVEMBER"
        11 -> monthAux = "DECEMBER"
        else -> monthAux = ""
    }
    return "$day $monthAux"
}

inline fun Long.toHourString(): String{

    val timestamp: Long = this

    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = timestamp

    val hours: Int = cal.get(Calendar.HOUR_OF_DAY)
    val minutes: Int = cal.get(Calendar.MINUTE)

    return "$hours:$minutes"
}

