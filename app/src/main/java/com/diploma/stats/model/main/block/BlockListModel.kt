package com.diploma.stats.model.main.block

import com.diploma.stats.model.main.friends.DataX

data class BlockListModel(
    val `data`: List<DataX>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)