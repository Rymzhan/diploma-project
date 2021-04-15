package com.thousand.bosch.model.list.top

data class Data(
    val first_name: String,
    val id: Int,
    val image: String?,
    val in_blacklist: Boolean,
    val in_friends: Boolean,
    val last_name: String,
    val login: String,
    val rating: Int,
    val scores: Int,
    val position: Int?
)