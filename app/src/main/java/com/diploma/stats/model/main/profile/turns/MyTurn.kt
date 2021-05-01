package com.diploma.stats.model.main.profile.turns

data class MyTurn(
    val creator_id: Int,
    val id: Int,
    val player_turn: Int,
    val left_time: String,
    val players: List<PlayerX>,
    val rounds: List<RoundX>,
    val should_create_round: Boolean,
    val status: String,
    val winner_id: Any
)