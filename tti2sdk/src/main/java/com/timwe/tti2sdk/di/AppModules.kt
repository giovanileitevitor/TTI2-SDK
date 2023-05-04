package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.mapper.*
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSource
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.*
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.domain.*
import com.timwe.tti2sdk.ui.avatar.AvatarViewModel
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.board.LeaderBoardViewModel
import com.timwe.tti2sdk.ui.destinations.DestinationViewModel
import com.timwe.tti2sdk.ui.helpwebview.HelpViewModel
import com.timwe.tti2sdk.ui.onboarding.OnBoardingViewModel
import com.timwe.tti2sdk.ui.home.HomeViewModel
import com.timwe.tti2sdk.ui.missions.MissionsViewModel
import com.timwe.tti2sdk.ui.missions.dailycheckups.DailyCheckupViewModel
import com.timwe.tti2sdk.ui.prizes.PrizesViewModel
import com.timwe.tti2sdk.ui.prizes.fragments.viewmodel.TabsPrizesViewModel
import com.timwe.tti2sdk.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
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
    private const val urlResponseToUrlAddress = "UrlResponseToUrlAddress"
    private const val cityInfoResponseToDestination = "CityInfoResponseToDestination"
    private const val listCityResponseToListCity = "listCityResponseToListCity"
    private const val boardsResponseToBoards = "BoardsResponseToBoards"
    private const val infosProfileHomeResponseToProfileInfo = "InfosProfileHomeResponseToProfileInfo"
    private const val redeemPrizeResponseToRedeemPrize = "RedeemPrizeResponseToRedeemPrize"
    private const val mapperDailyMission = "MissionResponseToDailyMission"

    val presentationModules = module {
        viewModel {
            AvatarViewModel(
                avatarUseCase = get(),
                sharedPrefUseCase = get(),
                eventReportUseCase = get()
            )
        }
        viewModel {
            TabsViewModel(avatarUseCase = get())
        }
        viewModel {
            PrizesViewModel(
                prizeUseCase = get(),
                eventReportUseCase = get()
            )
        }
        viewModel {
            TabsPrizesViewModel(prizeUseCase = get())
        }
        viewModel {
            HomeViewModel(
                avatarUseCase = get(),
                destinationsUseCase = get(),
            )
        }
        viewModel {
            SplashViewModel(
                urlUseCase = get(),
                sharedPrefUseCase = get(),
                eventReportUseCase = get()
            )
        }
        viewModel {
            MissionsViewModel(missionsUsecase = get())
        }
        viewModel {
            DailyCheckupViewModel()
        }
        viewModel {
            OnBoardingViewModel(
                context = androidContext(),
                sharedPrefUseCase = get()
            )
        }
        viewModel {
            LeaderBoardViewModel(
                boardsUseCase = get(),
                sharedPrefUseCase = get(),
                eventReportUseCase = get()
            )
        }
        viewModel {
            DestinationViewModel(
                destinationsUseCase = get(),
                sharedPrefUseCase = get()
            )
        }
        viewModel {
            HelpViewModel(
                sharedPrefUseCase = get(),
                eventReportUseCase = get()
            )
        }
    }

    val domainModules = module {

        single<AvatarUseCase>{
            AvatarUseCaseImpl(
                avatarDataSource = get(),
                sharedPrefDataSource = get()
            )
        }

        single<MissionsUseCase>{
            MissionsUseCaseImpl(missionsDataSource = get())
        }

        single<PrizeUseCase>{
            PrizeUseCaseImpl(prizeDataSource = get())
        }

        single<DestinationsUseCase>{
            DestinationsUseCaseImpl(
                cityDataSource = get(),
                sharedPrefDataSource = get()
            )
        }

        single<SharedPrefUseCase>{
            SharedPrefUseCaseImpl(sharedPrefDataSource = get())
        }

        single<UrlUseCase>{
            UrlUseCaseImpl(
                urlDataSource = get(),
                sharedPrefDataSource = get()
            )
        }

        single<BoardsUseCase>{
            BoardsUseCaseImpl(
                boardsDataSource = get()
            )
        }

        single<EventReportUseCase>{
            EventReportUseCaseImpl(
                eventDataSource = get()
            )
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

        single(named(prizesResponseToPrize)) {
            PrizesResponseToPrize()
        }

        single(named(urlResponseToUrlAddress)){
            UrlResponseToUrlAddress()
        }

        single(named(cityInfoResponseToDestination)){
            CityInfoResponseToDestination()
        }

        single(named(listCityResponseToListCity)){
            ListCityResponseToListCity()
        }

        single(named(boardsResponseToBoards)){
            BoardsResponseToBoards()
        }

        single(named(infosProfileHomeResponseToProfileInfo)){
            InfosProfileHomeResponseToProfileInfo()
        }

        single(named(redeemPrizeResponseToRedeemPrize)){
            RedeemPrizeResponseToRedeemPrize()
        }

        single(named(mapperDailyMission)){
            MissionResponseToDailyMission()
        }
    }

    val dataModules = module {

        single(named(apiService)){ RetrofitBuild.makeService<API>(baseUrl) }

        single<AvatarDataSource>{
            AvatarDataSourceImpl(
                api = get(named(apiService)),
                mapperAvatar = get(named(avatarResponseToAvatar)),
                mapperUserCreateAvatar = get(named(userCreateAvatarResponseToUserAndAvatar)),
                mapperAck = get(named(ackResponseToAck)),
                mapperProfileInfos = get(named(infosProfileHomeResponseToProfileInfo))
            )
        }

        single<MissionsDataSource>{
            MissionsDataSourceImpl(
                api = get(named(apiService)),
                mapperDailyMission = get(named(mapperDailyMission)),
                mapperMission = get(named(missionResponseToMission)),
                mapperAck = get(named(ackResponseToAck)),
            )
        }

        single<PrizeDataSource>{
            PrizeDataSourceImpl(
                api = get(named(apiService)),
                mapperPrizesResponseToPrize = get(named(prizesResponseToPrize)),
                mapperRedeemPrizeResponseToRedeemPrize = get(named(redeemPrizeResponseToRedeemPrize)),
            )
        }

        single<UrlDataSource>{
            UrlDataSourceImpl(
                api = get(named(apiService)),
                mapperUrls = get(named(urlResponseToUrlAddress))
            )
        }

        single<CityDataSource>{
            CityDataSourceImpl(
                api = get(named(apiService)),
                cityInfoResponseToDestination = get(named(cityInfoResponseToDestination)),
                listCityResponseToListCity = get(named(listCityResponseToListCity))
            )
        }

        single<BoardsDataSource>{
            BoardsDataSourceImpl(
                api = get(named(apiService)),
                mapperBoards = get(named(boardsResponseToBoards))
            )
        }

        single<EventDataSource>{
            EventDataSourceImpl(
                api = get(named(apiService)),
                mapperAckResponseToAck = get(named(ackResponseToAck))
            )
        }

    }

    val otherModules = module {
        single<SharedPrefDataSource>{
            SharedPrefDataSourceImpl(context = androidContext())
        }
    }

}