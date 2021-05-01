package com.diploma.stats.model.main.game.round.start

data class RoundStartModel(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)