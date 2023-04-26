package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Boards
import com.timwe.tti2sdk.data.net.api.Results

interface BoardsUseCase{
    suspend fun getBoards(): Results<Boards>
}