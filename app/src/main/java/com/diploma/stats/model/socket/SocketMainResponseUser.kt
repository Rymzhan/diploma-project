package com.diploma.stats.model.socket

import com.diploma.stats.global.socket.user.SocketUser

data class SocketMainResponseUser(
    val data: SocketUser,
    val event: String
)