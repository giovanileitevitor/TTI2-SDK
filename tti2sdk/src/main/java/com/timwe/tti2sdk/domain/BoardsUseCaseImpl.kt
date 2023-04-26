package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Boards
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.remote.BoardsDataSource

class BoardsUseCaseImpl(
    private val boardsDataSource: BoardsDataSource,
): BoardsUseCase{

    override suspend fun getBoards(): Results<Boards> {
        return boardsDataSource.getBoards()
    }
}