package com.timwe.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.timwe.tti2sdk.BuildConfig


class Utils {

    companion object {

        private const val TAG = "SDK"

        fun showLog(tag: String, msg: String){
            if(BuildConfig.DEBUG){
                Log.i(tag, msg)
            }
        }

        fun getDrawableFromId(context: Context?, id: Int): Drawable? {
            var drawable: Drawable? = null
            try {
                drawable = ContextCompat.getDrawable(context!!, id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return drawable
        }

        fun doAsync(activity: Activity, callback: DoAsyncCallback) {
            Thread {
                callback.doAsync()
                activity.runOnUiThread { callback.runOnUiAfterAsync() }
            }.start()
        }

        fun getDrawableFromUrl(
            activity: Activity,
            url: String?,
            listener: GenericListener<Drawable?>
        ) {
            Log.d(TAG, "getDrawableFromUrl: url")
            try {
                doAsync(activity, object : DoAsyncCallback {
                    var drawable: Drawable? = null

                    override fun doAsync() {
                        try {
                            Utils.showLog(TAG, "getDrawableFromUrl: doAsync")
                            drawable = Glide
                                .with(activity)
                                .asDrawable()
                                .load(url)
                                .override(Target.SIZE_ORIGINAL)
                                .submit()
                                .get()
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun runOnUiAfterAsync() {
                        Utils.showLog(TAG, "getDrawableFromUrl: runOnUiAfterAsync$drawable")
                        listener.onEvent(drawable)
                    }
                })
            } catch (e: java.lang.Exception) {
                Utils.showLog(TAG, "getDrawableFromUrl: Exception$e")
                e.printStackTrace()
                listener.onEvent(null)
            }
        }

        fun setImageViewFromUrl(context: Context, imageView: ImageView, url: String) {
            try {
                if (url != null) {
                    val options = RequestOptions()
                    Glide.with(context)
                        .load(url)
                        .apply(options)
                        .listener(object : RequestListener<Drawable?> {

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Utils.showLog(TAG, "setImageViewFromUrl onResourceReady: $url")
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Utils.showLog(TAG, "setImageViewFromUrl onLoadFailed: $e $url")
                                return false
                            }
                        }).into(imageView)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }




    }

}

interface DoAsyncCallback {
    fun doAsync()
    fun runOnUiAfterAsync()
}



