package com.diploma.stats.model.main.profile.turns

data class Data(
    val finished: List<Finished>?,
    val myTurn: List<MyTurn>?,
    val waiting: List<Waiting>?
)