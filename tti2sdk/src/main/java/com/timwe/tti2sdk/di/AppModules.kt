package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.mapper.UserCreateAvatarResponseToUserAndAvatar
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSource
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSourceImpl
import com.timwe.tti2sdk.domain.AvatarUseCase
import com.timwe.tti2sdk.domain.AvatarUseCaseImpl
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepository
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepositoryImpl
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.ui.avatar.AvatarViewModel
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.home.HomeViewModel
import com.timwe.tti2sdk.ui.missions.MissionsViewModel
import com.timwe.tti2sdk.ui.missions.dailycheckups.DailyCheckupViewModel
import com.timwe.tti2sdk.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModules {

    private const val apiService = "apiService"
    private const val baseUrl = com.timwe.tti2sdk.BuildConfig.BASE_URL
    private const val avatarResponseToAvatar = "AvatarResponseToAvatar"
    private const val userCreateAvatarResponseToUserAndAvatar = "UserCreateAvatarResponseToUserAndAvatar"

    val presentationModules = module {
        viewModel {
            AvatarViewModel(avatarUseCase = get())
        }
        viewModel {
            TabsViewModel(avatarUseCase = get())
        }
        viewModel {
            HomeViewModel(localRepository = get())
        }
        viewModel {
            SplashViewModel()
        }
        viewModel {
            MissionsViewModel(missionsUsecase = get())
        }
        viewModel {
            DailyCheckupViewModel()
        }
    }

    val domainModules = module {
        single<AvatarUseCase>{
            AvatarUseCaseImpl(avatarDataSource = get())
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

        single(named(apiService)) { RetrofitBuild.makeService<API>(baseUrl) }

        single<AvatarDataSource> {
            AvatarDataSourceImpl(
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