package com.diploma.stats.model.main.game.start.main

import com.diploma.stats.model.main.game.round.start.Question

data class Round(
    val category: Category,
    val creator_id: Int,
    val id: Int,
    val player_turn: Int?,
    val questions: List<Question>,
    val user_answers: List<UserAnswer>?
)