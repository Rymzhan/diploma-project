package com.diploma.stats.model.list.top

data class TopListResponse(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)