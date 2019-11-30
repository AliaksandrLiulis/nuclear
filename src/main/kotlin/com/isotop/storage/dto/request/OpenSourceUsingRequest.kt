package com.isotop.storage.dto.request

data class OpenSourceUsingRequest (
    val storageCode: Int,
    val containerCode: Int,
    val openSourceUsing: Int,
    val sourceActivity: Double
)