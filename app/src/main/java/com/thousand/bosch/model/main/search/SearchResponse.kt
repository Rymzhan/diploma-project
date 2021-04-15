package com.thousand.bosch.model.main.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("data")
    val `data`: List<SearchUser>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)