package com.diploma.stats.model.main.profile.turns

data class RoundXX(
    val category: CategoryXX,
    val creator_id: Int,
    val id: Int,
    val player_turn: Int,
    val questions: List<QuestionX>,
    val user_answers: List<Any>
)