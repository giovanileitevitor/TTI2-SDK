package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.di.AppModules.dataModules
import com.timwe.tti2sdk.di.AppModules.domainModules
import com.timwe.tti2sdk.di.AppModules.mapperModules
import com.timwe.tti2sdk.di.AppModules.otherModules
import com.timwe.tti2sdk.di.AppModules.presentationModules
import org.koin.core.module.Module

object AppComponent {

    fun getAllModules(): List<Module> = listOf(*getAppModules())

    private fun getAppModules(): Array<Module>{
        return arrayOf(
            presentationModules,
            domainModules,
            mapperModules,
            dataModules,
            otherModules
        )
    }
}