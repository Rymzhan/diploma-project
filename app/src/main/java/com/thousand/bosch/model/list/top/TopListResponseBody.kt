package com.thousand.bosch.model.list.top

import com.google.gson.annotations.SerializedName
import com.thousand.bosch.model.auth.login.User

data class TopListResponseBody(
    @SerializedName("users")
    val users: List<Data>? = null,
    @SerializedName("current_user")
    val currentUser: User? = null
)