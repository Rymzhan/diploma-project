package com.diploma.stats.model.main.profile.turns

data class Question(
    val answers: List<Answer>,
    val id: Int,
    val title: String
)