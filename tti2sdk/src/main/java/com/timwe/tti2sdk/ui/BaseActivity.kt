package com.timwe.tti2sdk.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.di.MyApplication
import java.util.Locale

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLanguage(MyApplication.instance?.userProfile?.lang ?: "en")
    }

    private fun setupLanguage(language: String){
        val locale = Locale(language)
        val configuration = Configuration()
        Locale.setDefault(locale)
        configuration.setLocale(locale)
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
    }
}
