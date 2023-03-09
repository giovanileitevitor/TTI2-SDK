package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.model.response.AvatarResponse
import com.timwe.tti2sdk.data.net.data.Mapper
import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
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
    const val baseUrl = com.timwe.tti2sdk.BuildConfig.BASE_URL
    const val avatarResponseToAvatar = "AvatarResponseToAvatar"

    val presentationModules = module {
        //Add all viewModels here
        viewModel {
            AvatarViewModel(get())
        }
    }

    val domainModules = module {
        //Add all usecases and wrappers here
        single<RepoRepository>{
            RepoRepositoryImpl(repoRemoteDataSource = get())
        }
    }

    val mapperModules = module {
        single(named(avatarResponseToAvatar)){
            AvatarResponseToAvatar()
        }
    }

    val dataModules = module {
        //Add all repositories and data sources here
        //single { LocalDataBase.createDataBase(androidContext())}
        single(named(apiService)) { RetrofitBuild.makeService<API>(baseUrl) }

        single<RepoRemoteDataSource> {
            RepoRemoteDataSourceImpl(
                get(named(apiService)),
                get(named(avatarResponseToAvatar))
            )
        }

    }

    val otherModules = module {
        //Add all Third Part modules here
    }

}