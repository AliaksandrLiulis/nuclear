package com.isotop.storage.dto.request

import java.time.LocalDate

data class MakeSourceRequest(
    val storageCode: Int?,
    val passportNumber: String,
    val serialNumber: String,
    val makeTypeCode: Int,
    val nuclideTypeCode: Int,
    val makeDate: LocalDate
)