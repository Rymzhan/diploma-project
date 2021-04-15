package com.thousand.bosch.model.socket

import com.thousand.bosch.global.socket.user.SocketUser

data class SocketMainResponseUser(
    val data: SocketUser,
    val event: String
)