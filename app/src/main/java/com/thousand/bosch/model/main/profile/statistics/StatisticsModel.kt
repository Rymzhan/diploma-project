package com.thousand.bosch.model.main.profile.statistics

data class StatisticsModel(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)