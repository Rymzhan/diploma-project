package com.thousand.bosch.model.main.game.start.friend

data class Data(
    val creator_id: Int,
    val id: Int,
    val player_turn: Int,
    val players: List<Player>,
    val rounds: List<Any>,
    val should_create_round: Boolean,
    val status: String
)