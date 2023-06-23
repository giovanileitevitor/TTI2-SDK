package com.timwe.tti2sdk.di

import android.app.Application
import android.content.res.Configuration
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import com.timwe.init.UserProfile
import com.timwe.tti2sdk.di.AppComponent.getAllModules
import com.timwe.utils.Utils
import com.timwe.utils.WifiService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import java.util.Locale

class MyApplication: Application() {

    companion object {
        var instance: MyApplication? = null
        private const val EN = "en"
        private const val IDN = "idn"
    }

    var isDebug: Boolean = false
    var userProfile: UserProfile? = null
    var token: String = ""

    override fun onCreate() {
        super.onCreate()
        initDI()
        initRive()
        initWifiService()
        initLanguageSelector()
        instance = this
    }

    private fun initWifiService() {
        WifiService.instance.initializeWithApplicationContext(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    private fun initDI(){
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            androidFileProperties()
            koin.loadModules(getAllModules())
            koin.createRootScope()
        }
    }

    private fun initRive(){
        AppInitializer.getInstance(this@MyApplication)
            .initializeComponent(
                RiveInitializer::class.java
            )
    }

    private fun initLanguageSelector(){
        userProfile?.lang.let {
            when(it){
                EN -> {
                    setLocale(language = it)
                }
                IDN -> {
                   setLocale(language = it)
                }
                else -> {
                    setLocale(language = EN)
                }
            }
        }
    }

    private fun setLocale(language: String){
        val locale = Locale(language)
        val configuration = Configuration()
        Locale.setDefault(locale)
        configuration.setLocale(locale)
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
    }

}