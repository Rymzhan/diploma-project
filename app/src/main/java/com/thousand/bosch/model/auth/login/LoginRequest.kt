package com.thousand.bosch.model.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("device_type")
    val deviceType: String = "android",
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("workplace")
    val workplace: String? = null,
    @SerializedName("country_id")
    val countryId: Int? = null,
    @SerializedName("city_id")
    val cityId: Int? = null,
    @SerializedName("registration_token")
    val registrationToken: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("organization")
    val organization: String? = null,
    @SerializedName("login")
    val login: String? = null
)