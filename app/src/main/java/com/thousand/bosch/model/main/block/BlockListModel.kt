package com.thousand.bosch.model.main.block

import com.thousand.bosch.model.main.friends.DataX

data class BlockListModel(
    val `data`: List<DataX>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)