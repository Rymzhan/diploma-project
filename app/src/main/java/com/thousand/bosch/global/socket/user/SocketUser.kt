package com.thousand.bosch.global.socket.user

data class SocketUser(
    val first_name: String,
    val fullname: String,
    val id: Int,
    val image: String,
    val in_blacklist: Boolean,
    val in_friends: Boolean,
    val last_name: String,
    val login: String,
    val rating: Int,
    val rating_coefficient: Int,
    val scores: Int
)