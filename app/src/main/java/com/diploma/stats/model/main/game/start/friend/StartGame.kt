package com.diploma.stats.model.main.game.start.friend

data class StartGame(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)