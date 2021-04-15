package com.thousand.bosch.model.main.profile.turns

data class Round(
    val category: Category,
    val creator_id: Int,
    val id: Int,
    val player_turn: Any,
    val user_answers: List<UserAnswer>
)