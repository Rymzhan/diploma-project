package com.thousand.bosch.model.main.profile.turns

data class RoundX(
    val category: CategoryX,
    val creator_id: Int,
    val id: Int,
    val player_turn: Int,
    val questions: List<Question>,
    val user_answers: List<UserAnswerX>
)