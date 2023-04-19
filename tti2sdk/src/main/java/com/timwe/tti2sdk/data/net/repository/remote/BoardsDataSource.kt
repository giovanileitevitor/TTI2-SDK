package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.ui.board.Boards

interface BoardsDataSource {

    suspend fun getBoards(msIsdn: Long, lang: String, tier: String): Results<Boards>

}