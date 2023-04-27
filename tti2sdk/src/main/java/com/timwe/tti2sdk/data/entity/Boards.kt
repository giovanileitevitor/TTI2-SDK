package com.timwe.tti2sdk.data.entity

data class Boards(
    val id: Int,
    val yourPlace: YourPlace,
    val boardAll: List<Board>,
    val boardToday: List<Board>,
    val boardWeek: List<Board>,
    val boardMonth: List<Board>
)

data class Board(
    val counter: Long,
    val boardName: String,
    val boardId: String,
    val kmBoard: Long,
    val distanceUnit: String
)

data class YourPlace(
    val position: Long,
    val yourName: String,
    val yourId: String,
    val yourDistance: Long,
    val distanceUnit: String
)
