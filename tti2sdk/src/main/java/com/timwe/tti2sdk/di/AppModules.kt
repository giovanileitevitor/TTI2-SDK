package com.timwe.tti2sdk.di

import com.timwe.tti2sdk.data.net.data.RetrofitBuild
import com.timwe.tti2sdk.data.net.mapper.AckResponseToAck
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.mapper.BoardsResponseToBoards
import com.timwe.tti2sdk.data.net.mapper.CityInfoResponseToDestination
import com.timwe.tti2sdk.data.net.mapper.InfosProfileHomeResponseToProfileInfo
import com.timwe.tti2sdk.data.net.mapper.ListCityResponseToListCity
import com.timwe.tti2sdk.data.net.mapper.MissionResponseToMission
import com.timwe.tti2sdk.data.net.mapper.PrizesResponseToPrize
import com.timwe.tti2sdk.data.net.mapper.RedeemPrizeResponseToRedeemPrize
import com.timwe.tti2sdk.data.net.mapper.SkipResponseToSkip
import com.timwe.tti2sdk.data.net.mapper.UrlResponseToUrlAddress
import com.timwe.tti2sdk.data.net.mapper.UserCreateAvatarResponseToUserAndAvatar
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSource
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.BoardsDataSource
import com.timwe.tti2sdk.data.net.repository.remote.BoardsDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.CityDataSource
import com.timwe.tti2sdk.data.net.repository.remote.CityDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.EventDataSource
import com.timwe.tti2sdk.data.net.repository.remote.EventDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.MissionsDataSource
import com.timwe.tti2sdk.data.net.repository.remote.MissionsDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.PrizeDataSource
import com.timwe.tti2sdk.data.net.repository.remote.PrizeDataSourceImpl
import com.timwe.tti2sdk.data.net.repository.remote.UrlDataSource
import com.timwe.tti2sdk.data.net.repository.remote.UrlDataSourceImpl
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.domain.AvatarUseCase
import com.timwe.tti2sdk.domain.AvatarUseCaseImpl
import com.timwe.tti2sdk.domain.BoardsUseCase
import com.timwe.tti2sdk.domain.BoardsUseCaseImpl
import com.timwe.tti2sdk.domain.DestinationsUseCase
import com.timwe.tti2sdk.domain.DestinationsUseCaseImpl
import com.timwe.tti2sdk.domain.EventReportUseCase
import com.timwe.tti2sdk.domain.EventReportUseCaseImpl
import com.timwe.tti2sdk.domain.MissionsUseCase
import com.timwe.tti2sdk.domain.MissionsUseCaseImpl
import com.timwe.tti2sdk.domain.PrizeUseCase
import com.timwe.tti2sdk.domain.PrizeUseCaseImpl
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import com.timwe.tti2sdk.domain.SharedPrefUseCaseImpl
import com.timwe.tti2sdk.domain.UrlUseCase
import com.timwe.tti2sdk.domain.UrlUseCaseImpl
import com.timwe.tti2sdk.ui.avatar.AvatarViewModel
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.board.LeaderBoardViewModel
import com.timwe.tti2sdk.ui.destinations.DestinationViewModel
import com.timwe.tti2sdk.ui.helpwebview.HelpViewModel
import com.timwe.tti2sdk.ui.home.HomeViewModel
import com.timwe.tti2sdk.ui.missions.MissionsViewModel
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.tti2sdk.ui.onboarding.OnBoardingViewModel
import com.timwe.tti2sdk.ui.prizes.PrizesViewModel
import com.timwe.tti2sdk.ui.prizes.fragments.viewmodel.TabsPrizesViewModel
import com.timwe.tti2sdk.ui.splash.SplashViewModel
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
    private const val mapperSkipResponse = "SkipResponseToSkip"

    val presentationModules = module {
        viewModel {
            AvatarViewModel(
                avatarUseCase = get(),
                sharedPrefUseCase = get(),
                eventReportUseCase = get()
            )
        }
        viewModel {
            TabsViewModel(
                avatarUseCase = get()
            )
        }
        viewModel {
            PrizesViewModel(
                prizeUseCase = get(),
                eventReportUseCase = get()
            )
        }
        viewModel {
            TabsPrizesViewModel(
                prizeUseCase = get()
            )
        }
        viewModel {
            HomeViewModel(
                avatarUseCase = get(),
                destinationsUseCase = get(),
                eventReportUseCase = get(),
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
            MissionsViewModel(
                missionsUsecase = get(),
                sharedPrefUseCase = get()
            )
        }
        viewModel {
            DailyViewModel(
                missionsUseCase = get()
            )
        }
        viewModel {
            OnBoardingViewModel(
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

        single(named(mapperSkipResponse)){
            SkipResponseToSkip()
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
                mapperMission = get(named(missionResponseToMission)),
                mapperAck = get(named(ackResponseToAck)),
                mapperSkipResponse = get(named(mapperSkipResponse))
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