package com.thousand.bosch.model.list.top

data class Top20ListResponse(
    val `data`: TopListResponseBody,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)