package com.thousand.bosch.model.main.profile.turns

data class Waiting(
    val creator_id: Int,
    val id: Int,
    val player_turn: Int,
    val left_time: String,
    val players: List<PlayerXX>,
    val rounds: List<RoundXX>,
    val should_create_round: Boolean,
    val status: String,
    val winner_id: Any
)