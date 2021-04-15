package com.thousand.bosch.model.socket

import com.thousand.bosch.model.main.profile.turns.Data

data class SocketMainResponseGameList(
    val data: Data,
    val event: String
)