package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.repository.RepoRemoteDataSource
import com.timwe.tti2sdk.data.net.repository.RepoRemoteDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.RepoRepository
import com.timwe.tti2sdk.data.net.repository.RepoRepositoryImpl
import com.timwe.tti2sdk.data.net.services.API
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModules {

    const val apiService = "apiService"
    const val baseUrl = "http://lx-prp-idn-tti-external-ws-tc-01.timwe.com:8081/webapp-dap-id-telkomsel-tti-app/tti/"

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
        single(named(apiService)) { RetrofitBuild.makeService<API>(baseUrl) }

        single<RepoRemoteDataSource> {
            RepoRemoteDataSourceImpl(
                get(named(apiService)),
                get(named("avatar_response")),
            )
        }

        single<RepoRepository> { RepoRepositoryImpl(get()) }

    }

    val otherModules = module {
        //Add all Third Part modules here
    }

}