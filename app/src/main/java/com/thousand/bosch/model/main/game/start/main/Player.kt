package com.thousand.bosch.model.main.game.start.main

data class Player(
    val first_name: String,
    val id: Int,
    val image: String,
    val last_name: String,
    val login: String,
    val points: Int,
    val scores: Int
)