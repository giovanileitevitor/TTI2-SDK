package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.model.response.BoardsResponse
import com.timwe.tti2sdk.data.net.data.Mapper
import com.timwe.tti2sdk.ui.board.Board
import com.timwe.tti2sdk.ui.board.Boards
import com.timwe.tti2sdk.ui.board.YourPlace

class BoardsResponseToBoards: Mapper<BoardsResponse, Boards>() {

    override fun transform(item: BoardsResponse): Boards{
        return Boards(
            id = item.id,
            yourPlace = getYourPlace(item = item),
            boardAll = getAllBoards(item = item),
            boardMonth = getMonthBoards(item = item),
            boardToday = getTodayBoards(item = item),
            boardWeek = getWeekBoards(item = item)
        )
    }

    private fun getYourPlace(item: BoardsResponse): YourPlace{
        var yourPlace = YourPlace(0, "", "",0, "" )
        item.leaderboards.let{
            item.leaderboards.forEach {
                when (it.name) {
                    ALL_TIME -> {
                        yourPlace = YourPlace(
                            position = it.userBoardInfo.boardPosition,
                            yourName = it.userBoardInfo.username,
                            yourId = it.userBoardInfo.plateNumber,
                            yourDistance = it.userBoardInfo.score,
                            distanceUnit = "km"
                        )
                    }

                    MONTH -> {
                        yourPlace = YourPlace(
                            position = it.userBoardInfo.boardPosition,
                            yourName = it.userBoardInfo.username,
                            yourId = it.userBoardInfo.plateNumber,
                            yourDistance = it.userBoardInfo.score,
                            distanceUnit = "km"
                        )
                    }

                    TODAY -> {
                        yourPlace = YourPlace(
                            position = it.userBoardInfo.boardPosition,
                            yourName = it.userBoardInfo.username,
                            yourId = it.userBoardInfo.plateNumber,
                            yourDistance = it.userBoardInfo.score,
                            distanceUnit = "km"
                        )
                    }

                    WEEK -> {
                        yourPlace = YourPlace(
                            position = it.userBoardInfo.boardPosition,
                            yourName = it.userBoardInfo.username,
                            yourId = it.userBoardInfo.plateNumber,
                            yourDistance = it.userBoardInfo.score,
                            distanceUnit = "km"
                        )
                    }

                    else -> {
                        yourPlace = YourPlace(
                            position = it.userBoardInfo.boardPosition,
                            yourName = it.userBoardInfo.username,
                            yourId = it.userBoardInfo.plateNumber,
                            yourDistance = it.userBoardInfo.score,
                            distanceUnit = "km"
                        )
                    }
                }
            }
        }

        return yourPlace
    }

    private fun getAllBoards(item: BoardsResponse): List<Board>{
        val allboards = mutableListOf<Board>()
        item.leaderboards.forEach{
            if(it.name == ALL_TIME){
                it.boardInfo.forEach { boardInfo ->
                    allboards.add(
                        Board(
                            counter = boardInfo.boardPosition,
                            boardName = boardInfo.username,
                            boardId = boardInfo.plateNumber,
                            kmBoard = boardInfo.score,
                            distanceUnit = "km"
                        )
                    )
                }
            }
        }

        return allboards
    }

    private fun getMonthBoards(item: BoardsResponse): List<Board>{
        val monthBoards = mutableListOf<Board>()
        item.leaderboards.forEach{
            if(it.name == MONTH){
                it.boardInfo.forEach { boardInfo ->
                    monthBoards.add(
                        Board(
                            counter = boardInfo.boardPosition,
                            boardName = boardInfo.username,
                            boardId = boardInfo.plateNumber,
                            kmBoard = boardInfo.score,
                            distanceUnit = "km"
                        )
                    )
                }
            }
        }

        return monthBoards
    }

    private fun getTodayBoards(item: BoardsResponse): List<Board>{
        val todayBoards = mutableListOf<Board>()
        item.leaderboards.forEach{
            if(it.name == TODAY){
                it.boardInfo.forEach { boardInfo ->
                    todayBoards.add(
                        Board(
                            counter = boardInfo.boardPosition,
                            boardName = boardInfo.username,
                            boardId = boardInfo.plateNumber,
                            kmBoard = boardInfo.score,
                            distanceUnit = "km"
                        )
                    )
                }
            }
        }

        return todayBoards
    }

    private fun getWeekBoards(item: BoardsResponse): List<Board>{
        val weekBoards = mutableListOf<Board>()
        item.leaderboards.forEach{
            if(it.name == WEEK){
                it.boardInfo.forEach { boardInfo ->
                    weekBoards.add(
                        Board(
                            counter = boardInfo.boardPosition,
                            boardName = boardInfo.username,
                            boardId = boardInfo.plateNumber,
                            kmBoard = boardInfo.score,
                            distanceUnit = "km"
                        )
                    )
                }
            }
        }

        return weekBoards
    }

    companion object{
        private const val ALL_TIME = "All Time"
        private const val TODAY = "Today"
        private const val WEEK = "Week"
        private const val MONTH = "Month"
    }
}