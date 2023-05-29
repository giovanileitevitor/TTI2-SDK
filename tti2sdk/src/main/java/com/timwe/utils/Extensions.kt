package com.timwe.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


inline fun View.onDebouncedListener(
    delayInClick: Long = 200L,
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

inline fun Int.formatToKm(): String{
    val kns: Int = this
    val kmFormated = DecimalFormat("#,###", DecimalFormatSymbols(Locale.GERMANY)).format(kns)
    return "$kmFormated km"
}

fun convertDpToPixel(context: Context, dp: Int): Int {
    var px = 0
    try {
        val densityDpiFloat = context.resources.displayMetrics.densityDpi.toFloat()
        val floatPx = dp * densityDpiFloat / DisplayMetrics.DENSITY_DEFAULT
        px = floatPx.toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("SDK", "convertDpToPixel: $e")
    }
    return px
}

fun convertPixelsToDp(context: Context, px: Int): Int {
    var dp = 0
    try {
        val densityDpiFloat = context.resources.displayMetrics.densityDpi.toFloat()
        val floatPx = px / densityDpiFloat / DisplayMetrics.DENSITY_DEFAULT
        dp = floatPx.toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("SDK", "convertPixelsToDp: $e")
    }
    return dp
}

