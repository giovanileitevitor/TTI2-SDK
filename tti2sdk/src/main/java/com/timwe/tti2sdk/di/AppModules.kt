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

    private const val apiService = "apiService"
    private const val baseUrl = com.timwe.tti2sdk.BuildConfig.BASE_URL
    private const val avatarResponseToAvatar = "AvatarResponseToAvatar"

    val presentationModules = module {
        viewModel {
            AvatarViewModel(repoRepository = get())
        }
    }

    val domainModules = module {
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
        single(named(apiService)) { RetrofitBuild.makeService<API>(baseUrl) }

        single<RepoRemoteDataSource> {
            RepoRemoteDataSourceImpl(
                api = get(named(apiService)),
                mapperAvatar = get(named(avatarResponseToAvatar))
            )
        }

    }

    val otherModules = module {
        //Add all Third Part modules here
    }

}