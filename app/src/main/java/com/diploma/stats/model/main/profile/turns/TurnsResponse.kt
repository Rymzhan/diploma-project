package com.diploma.stats.model.main.profile.turns

data class TurnsResponse(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)