package com.diploma.stats.model.main.friends

import com.google.gson.annotations.SerializedName

data class FriendsResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)