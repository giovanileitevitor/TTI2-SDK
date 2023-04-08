package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.mapper.*
import com.timwe.tti2sdk.data.net.mapper.AckResponseToAck
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.mapper.MissionResponseToMission
import com.timwe.tti2sdk.data.net.mapper.UserCreateAvatarResponseToUserAndAvatar
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSource
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepository
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepositoryImpl
import com.timwe.tti2sdk.data.net.repository.remote.*
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.domain.*
import com.timwe.tti2sdk.ui.avatar.AvatarViewModel
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.board.LeaderBoardViewModel
import com.timwe.tti2sdk.ui.destinations.DestinationViewModel
import com.timwe.tti2sdk.ui.onboarding.OnBoardingViewModel
import com.timwe.tti2sdk.ui.home.HomeViewModel
import com.timwe.tti2sdk.ui.missions.MissionsViewModel
import com.timwe.tti2sdk.ui.missions.dailycheckups.DailyCheckupViewModel
import com.timwe.tti2sdk.ui.prizes.PrizesViewModel
import com.timwe.tti2sdk.ui.prizes.fragments.viewmodel.TabsPrizesViewModel
import com.timwe.tti2sdk.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModules {

    private const val apiService = "apiService"
    private const val baseUrl = com.timwe.tti2sdk.BuildConfig.BASE_URL
    private const val avatarResponseToAvatar = "AvatarResponseToAvatar"
    private const val userCreateAvatarResponseToUserAndAvatar = "UserCreateAvatarResponseToUserAndAvatar"
    private const val missionResponseToMission = "MissionResponseToMission"
    private const val ackResponseToAck = "AckResponseToAck"
    private const val prizesResponseToPrize = "PrizesResponseToPrize"

    val presentationModules = module {
        viewModel {
            AvatarViewModel(avatarUseCase = get())
        }
        viewModel {
            TabsViewModel(avatarUseCase = get())
        }
        viewModel {
            PrizesViewModel(prizeUseCase = get())
        }
        viewModel {
            TabsPrizesViewModel(prizeUseCase = get())
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
        viewModel {
            OnBoardingViewModel(
                context = androidApplication(),
                sharedPrefUseCase = get()
            )
        }
        viewModel {
            LeaderBoardViewModel()
        }
        viewModel {
            DestinationViewModel(destinationsUsecase = get())
        }
    }

    val domainModules = module {
        single<AvatarUseCase>{
            AvatarUseCaseImpl(avatarDataSource = get())
        }

        single<MissionsUseCase>{
            MissionsUseCaseImpl(missionsDataSource = get())
        }

        single<PrizeUseCase> {
            PrizeUseCaseImpl(prizeDataSource = get())
        }

        single<DestinationsUseCase>{
            DestinationsUseCaseImpl()
        }

        single<SharedPrefUseCase>{
            SharedPrefUseCaseImpl(sharedPrefRepository = get())
        }
    }

    val mapperModules = module {
        single(named(avatarResponseToAvatar)){
            AvatarResponseToAvatar()
        }
        single(named(userCreateAvatarResponseToUserAndAvatar)){
            UserCreateAvatarResponseToUserAndAvatar()
        }

        single(named(missionResponseToMission)){
            MissionResponseToMission()
        }

        single(named(ackResponseToAck)){
            AckResponseToAck()
        }

        single(named(prizesResponseToPrize)){
            PrizesResponseToPrize()
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

        single<MissionsDataSource>{
            MissionsDataSourceImpl(
                api = get(named(apiService)),
                mapperMission = get(named(missionResponseToMission)),
                mapperAck = get(named(ackResponseToAck))
            )
        }

        single<PrizeDataSource>{
            PrizeDataSourceImpl(
                api = get(named(apiService)),
                mapperPrizesResponseToPrize = get(named(prizesResponseToPrize))
            )
        }

    }

    val otherModules = module {
        single<SharedPrefRepository> {
            SharedPrefRepositoryImpl(context = get())
        }
    }

}