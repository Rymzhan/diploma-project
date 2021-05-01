package com.diploma.stats.model.auth.login

import com.google.gson.annotations.SerializedName

data class MainResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)