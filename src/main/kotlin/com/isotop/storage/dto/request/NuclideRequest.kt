package com.isotop.storage.dto.request

data class NuclideRequest (
    val nuclideTypeCode: Int,
    val nuclideType: String?,
    val nuclideTypeName: String?
)