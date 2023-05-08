package com.timwe.tti2sdk.di

import android.app.Application
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

class MyApplication: Application() {

    companion object {
        var instance: MyApplication? = null
    }

    var isDebug: Boolean = false
    var userProfile: UserProfile? = null
    var token: String = ""

    override fun onCreate() {
        super.onCreate()
        initDI()
        initRive()
        initWifiService()
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

}