package com.diploma.stats.model.socket

import com.diploma.stats.model.main.profile.turns.Data

data class SocketMainResponseGameList(
    val data: Data,
    val event: String
)