package com.timwe.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.timwe.tti2sdk.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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

        fun isExternalStorageWritable(): Boolean {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

        fun saveImageExternal(image: Bitmap, context: Context): Uri? {
            var uri: Uri? = null
            try {
                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_avatar.png")
                val stream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.PNG, 90, stream)
                stream.close()
                uri = Uri.fromFile(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return uri
        }

        fun saveImage(image: Bitmap, context: Context): Uri? {
            val imagesFolder = File(context.cacheDir, "images")
            var uri: Uri? = null
            try {
                imagesFolder.mkdirs()
                val file = File(imagesFolder, "my_avatar.png")
                val stream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.PNG, 90, stream)
                stream.flush()
                stream.close()
                uri = FileProvider.getUriForFile(context, "${context.applicationContext.packageName}.fileprovider", file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return uri
        }

    }

}