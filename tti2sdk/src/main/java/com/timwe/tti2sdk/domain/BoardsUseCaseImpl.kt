package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.remote.BoardsDataSource
import com.timwe.tti2sdk.ui.board.Boards

class BoardsUseCaseImpl(
    private val boardsDataSource: BoardsDataSource,
    private val sharedPrefDataSource: SharedPrefDataSource
): BoardsUseCase{

    override suspend fun getBoards(): Results<Boards> {
        return boardsDataSource.getBoards(
            msIsdn = sharedPrefDataSource.getMsIsdn() ?: 0,
            lang = sharedPrefDataSource.getLanguage(),
            tier = sharedPrefDataSource.getAvatarTier()
        )
    }
}