package com.thousand.bosch.model.main.profile.turns

data class Finished(
    val creator_id: Int,
    val id: Int,
    val player_turn: Any,
    val finish_status: String?,
    val players: List<Player>,
    val rounds: List<Round>,
    val should_create_round: Boolean,
    val status: String,
    val winner_id: Int?
)