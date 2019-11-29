package com.isotop.storage.dto.request

data class MakeSourceRequest (
    val passportNumber: String,
    val serialNumber: String,
    val makeTypeCode: Int,
    val nuclideTypeCode: Int
)