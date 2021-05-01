package com.diploma.stats.model.web_view

data class WebViewModel(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)