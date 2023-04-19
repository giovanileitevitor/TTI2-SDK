package com.timwe.tti2sdk.data.model.response

import java.io.Serializable

data class BoardsResponse(
    val id: Int = 0,
    val popups: Any? = null,
    val errorTO: Any? = null,
    val newMessages: Boolean,
    val userChangedCity: Boolean,
    val leaderboards: List<Leaderboard>
): Serializable

data class Leaderboard (
    val name: String,
    val boardType: Any? = null,
    val icon: String,
    val userBoardInfo: BoardInfo,
    val boardInfo: List<BoardInfo>
): Serializable

data class BoardInfo (
    val username: String,
    val avatar: Any? = null,
    val plateNumber: String,
    val topPercentage: Any? = null,
    val topPercentageText: String? = null,
    val boardPosition: Long,
    val score: Long
): Serializable