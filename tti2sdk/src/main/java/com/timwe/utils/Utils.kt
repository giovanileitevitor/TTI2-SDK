package com.timwe.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.timwe.tti2sdk.BuildConfig
import org.json.JSONException
import org.json.JSONObject


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

        fun showLog(tag: String, msg: String){
            if(BuildConfig.DEBUG){
                Log.i(tag, msg)
            }
        }

    }


}