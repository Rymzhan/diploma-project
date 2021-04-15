package com.thousand.bosch.model.main.game.round.start

data class Data(
    val category: Category,
    val creator_id: Int,
    val id: Int,
    val player_turn: Int,
    val questions: List<Question>
)