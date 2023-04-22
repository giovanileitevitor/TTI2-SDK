package com.timwe.tti2sdk.data.entity

data class AvatarStatus(
    val msIsdn: Long,
    val img: String?,
    val kmAtual: Float,
    val kmPercorrido: Float?,
    val avatarPercentual: Int,
    val avatarName: String,
    val avatarTeam: String,
    val avatarTier: String
)