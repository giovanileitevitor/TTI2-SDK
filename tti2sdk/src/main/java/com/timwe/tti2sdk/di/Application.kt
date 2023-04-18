package com.timwe.tti2sdk.di

import androidx.multidex.MultiDexApplication
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import com.timwe.init.UserProfile
import com.timwe.tti2sdk.di.AppComponent.getAllModules
import com.timwe.utils.WifiService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

open class Application: MultiDexApplication(), KoinComponent {

    var myApplication: Application? = null

    override fun onCreate() {
        super.onCreate()
        initDI()
        initRive()
        initWifiService()
        myApplication = this
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

    var isDebug : Boolean
        get() = this.isDebug
        set(value) {
            isDebug = value
        }

    var userProfileAux : UserProfile
        get() = this.userProfileAux
        set(userProfile : UserProfile) {
            userProfileAux.profileId = userProfile.profileId ?: ""
            userProfileAux.userMsisdn = userProfile.userMsisdn ?: ""
            userProfileAux.lang = userProfile.lang ?: ""
            userProfileAux.email = userProfile.email ?: ""
        }


}