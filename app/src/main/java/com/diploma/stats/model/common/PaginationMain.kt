package com.diploma.stats.model.common

import com.google.gson.annotations.SerializedName

data class PaginationMain(
    @SerializedName("data")
    val data: Pagination,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)