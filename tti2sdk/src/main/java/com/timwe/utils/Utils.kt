package com.timwe.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class Utils {

    companion object {

        private const val TAG = "Utils"

        fun getDrawableFromId(context: Context?, id: Int): Drawable? {
            var drawable: Drawable? = null
            try {
                drawable = ContextCompat.getDrawable(context!!, id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return drawable
        }


    }
}