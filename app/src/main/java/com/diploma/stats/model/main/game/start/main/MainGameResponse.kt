package com.diploma.stats.model.main.game.start.main

data class MainGameResponse(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)