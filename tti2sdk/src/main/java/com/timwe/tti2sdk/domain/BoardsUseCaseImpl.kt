package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.remote.BoardsDataSource
import com.timwe.tti2sdk.ui.board.Boards

class BoardsUseCaseImpl(
    private val boardsDataSource: BoardsDataSource,
): BoardsUseCase{

    override suspend fun getBoards(): Results<Boards> {
        return boardsDataSource.getBoards()
    }
}