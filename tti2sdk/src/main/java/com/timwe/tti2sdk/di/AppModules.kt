package com.timwe.tti2sdk.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules {

    val presentationModules = module {
        //Add all viewModels here
        //viewModel { ViewModelName(parameter = get() ) }
    }

    val domainModules = module {
        //Add all usecases and wrappers here
        //factory { }
    }

    val dataModules = module {
        //Add all repositories and data sources here
        //single { LocalDataBase.createDataBase(androidContext())}
    }

    val otherModules = module {
        //Add all Third Part modules here
    }
}