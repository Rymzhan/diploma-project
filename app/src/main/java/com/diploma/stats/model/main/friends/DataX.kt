package com.diploma.stats.model.main.friends

import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("in_blacklist")
    val in_blacklist: Boolean,
    @SerializedName("in_friends")
    val in_friends: Boolean,
    @SerializedName("last_name")
    val last_name: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("pivot")
    val pivot: Pivot,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("scores")
    val scores: Int
)