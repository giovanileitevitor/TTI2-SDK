package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Boards
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.BoardsResponseToBoards
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.MyApplication
import com.timwe.utils.Utils

class BoardsDataSourceImpl(
    private val api: API,
    private val mapperBoards: BoardsResponseToBoards
) : BoardsDataSource{

    override suspend fun getBoards(): Results<Boards> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/leaderboard")
        return api.getBoards(
            msisdn = MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang = MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
        ).create(mapperBoards)
    }

}