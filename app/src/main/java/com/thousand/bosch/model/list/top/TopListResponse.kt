package com.thousand.bosch.model.list.top

data class TopListResponse(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)