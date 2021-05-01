package com.diploma.stats.model.auth.login

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("expires_in")
    val expires_in: Int,
    @SerializedName("token_type")
    val token_type: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("registration_token")
    val registration_token: String,
    @SerializedName("reset_token")
    val reset_token: String

)
