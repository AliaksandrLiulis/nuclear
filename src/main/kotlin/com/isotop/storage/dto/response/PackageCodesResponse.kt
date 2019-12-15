package com.isotop.storage.dto.response

data class PackageCodesResponse(
    val packageCode: Int,
    val storageCode: Int,
    val containerCode: Int,
    val openSourceUsing: Int,
    val souceActivity: Double
)