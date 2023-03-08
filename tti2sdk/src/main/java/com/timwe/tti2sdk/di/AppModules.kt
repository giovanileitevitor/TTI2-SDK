package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.repository.RepoRemoteDataSource
import com.timwe.tti2sdk.data.net.repository.RepoRemoteDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.RepoRepository
import com.timwe.tti2sdk.data.net.repository.RepoRepositoryImpl
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.ui.avatar.AvatarViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

object AppModules {

    const val apiService = "apiService"
    const val baseUrl = "http://lx-prp-idn-tti-external-ws-tc-01.timwe.com:8081/webapp-dap-id-telkomsel-tti-app/tti/"
    const val baseUrlByGradle = com.timwe.tti2sdk.BuildConfig.BASE_URL    //mudar para esta URL no futuro

    val presentationModules = module {
        //Add all viewModels here
        viewModel { AvatarViewModel() }
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

        single<RepoRepository> {
            RepoRepositoryImpl(
                get()
            )
        }

    }

    val otherModules = module {
        //Add all Third Part modules here
    }

}