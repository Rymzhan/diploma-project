package com.thousand.bosch.model.main.friends

import com.google.gson.annotations.SerializedName

data class Pivot(
    @SerializedName("friend_id")
    val friend_id: Int,
    @SerializedName("user_id")
    val user_id: Int
)