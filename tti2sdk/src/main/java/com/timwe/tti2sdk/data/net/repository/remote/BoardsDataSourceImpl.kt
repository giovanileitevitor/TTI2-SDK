package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.BoardsResponseToBoards
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.Application
import com.timwe.tti2sdk.ui.board.Boards
import com.timwe.utils.Utils

class BoardsDataSourceImpl(
    private val api: API,
    private val mapperBoards: BoardsResponseToBoards,
    private val context: Context
) : BoardsDataSource{

    override suspend fun getBoards(): Results<Boards> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/leaderboard")
        return api.getBoards(
            msisdn =  (context as Application).getUserProfile().userMsisdn.toLong(),
            lang =  (context as Application).getUserProfile().lang,
            plan = (context as Application).getUserProfile().plan,
            tier =  (context as Application).getUserProfile().tier!!,
        ).create(mapperBoards)
    }

}