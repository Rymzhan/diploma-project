package com.thousand.bosch.model.main.profile.statistics

data class CategoryStat(
    val answers: Int,
    val correct_answers: Int,
    val games_count: Int,
    val id: Int,
    val image: String?,
    val title: String
)