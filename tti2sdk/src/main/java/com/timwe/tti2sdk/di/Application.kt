package com.timwe.tti2sdk.di

import androidx.multidex.MultiDexApplication
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import com.timwe.tti2sdk.di.AppComponent.getAllModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

open class Application: MultiDexApplication(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initDI()
        initRive()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    private fun initDI(){
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            androidFileProperties()
            koin.loadModules(getAllModules())
        }
    }

    private fun initRive(){
        AppInitializer.getInstance(this@Application)
            .initializeComponent(
                RiveInitializer::class.java
            )
    }
}