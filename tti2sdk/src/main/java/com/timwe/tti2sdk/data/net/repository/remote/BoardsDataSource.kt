package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.Boards
import com.timwe.tti2sdk.data.net.api.Results

interface BoardsDataSource {

    suspend fun getBoards(): Results<Boards>

}