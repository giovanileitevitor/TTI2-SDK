package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.ui.board.Boards

interface BoardsUseCase{
    suspend fun getBoards(): Results<Boards>
}