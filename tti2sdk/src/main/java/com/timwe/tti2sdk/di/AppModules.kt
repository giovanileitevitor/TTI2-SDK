package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.net.data.RetrofitBuild
import com.timwe.tti2sdk.net.repository.RepoRemoteDataSource
import com.timwe.tti2sdk.net.repository.RepoRemoteDataSourceImpl
import com.timwe.tti2sdk.net.repository.RepoRepository
import com.timwe.tti2sdk.net.repository.RepoRepositoryImpl
import com.timwe.tti2sdk.net.services.API
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModules {

    const val apiService = "apiService"

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
        single(named(apiService)) { RetrofitBuild.makeService<API>("") }

        single<RepoRemoteDataSource> {
            RepoRemoteDataSourceImpl(
                get(named(apiService)),
                get(named("example_response")),
            )
        }

        single<RepoRepository> { RepoRepositoryImpl(get()) }

    }

    val otherModules = module {
        //Add all Third Part modules here
    }

}