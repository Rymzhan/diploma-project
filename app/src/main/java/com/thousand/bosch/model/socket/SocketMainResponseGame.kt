package com.thousand.bosch.model.socket

import com.thousand.bosch.model.main.game.start.main.Data


data class SocketMainResponseGame(
    val data: Data,
    val event: String
)