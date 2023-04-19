package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.model.response.BoardsResponse
import com.timwe.tti2sdk.data.net.data.Mapper
import com.timwe.tti2sdk.ui.board.Boards

class BoardsResponseToBoards: Mapper<BoardsResponse, Boards>() {

    override fun transform(item: BoardsResponse): Boards{
        return Boards(
            id = item.id,
            boardAll = emptyList(),
            boardMonth = emptyList(),
            boardToday = emptyList(),
            boardWeek = emptyList()
        )
    }
}