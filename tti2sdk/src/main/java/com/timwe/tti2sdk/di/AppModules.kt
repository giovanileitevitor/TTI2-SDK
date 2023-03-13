package com.timwe.tti2sdk.di

import android.content.Context
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.model.response.AvatarResponse
import com.timwe.tti2sdk.data.net.data.Mapper
import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.mapper.UserCreateAvatarResponseToUserAndAvatar
import com.timwe.tti2sdk.data.net.repository.RepoRemoteDataSource
import com.timwe.tti2sdk.data.net.repository.RepoRemoteDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.RepoRepository
import com.timwe.tti2sdk.data.net.repository.RepoRepositoryImpl
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepository
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepositoryImpl
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.ui.avatar.AvatarViewModel
import com.timwe.tti2sdk.ui.home.HomeViewModel
import com.timwe.tti2sdk.ui.splash.SplashViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

object AppModules {

    private const val apiService = "apiService"
    private const val baseUrl = com.timwe.tti2sdk.BuildConfig.BASE_URL
    private const val avatarResponseToAvatar = "AvatarResponseToAvatar"
    private const val userCreateAvatarResponseToUserAndAvatar = "UserCreateAvatarResponseToUserAndAvatar"

    val presentationModules = module {
        viewModel {
            AvatarViewModel(repoRepository = get())
        }
        viewModel {
            HomeViewModel(localRepository = get())
        }
        viewModel {
            SplashViewModel()
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
        single(named(userCreateAvatarResponseToUserAndAvatar)){
            UserCreateAvatarResponseToUserAndAvatar()
        }
    }

    val dataModules = module {
        //Add all repositories and data sources here
        single(named(apiService)) { RetrofitBuild.makeService<API>(baseUrl) }

        single<RepoRemoteDataSource> {
            RepoRemoteDataSourceImpl(
                api = get(named(apiService)),
                mapperAvatar = get(named(avatarResponseToAvatar)),
                mapperUserCreateAvatar = get(named(userCreateAvatarResponseToUserAndAvatar))
            )
        }

    }

    val otherModules = module {
        single<SharedPrefRepository> {
            SharedPrefRepositoryImpl(context = get())
        }
    }

}