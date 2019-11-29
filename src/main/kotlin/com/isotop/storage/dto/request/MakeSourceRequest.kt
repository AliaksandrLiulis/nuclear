package com.isotop.storage.dto.request

import java.time.LocalDate

data class MakeSourceRequest (
    val passportNumber: String,
    val serialNumber: String,
    val makeDate: LocalDate?,
    val makeTypeCode: Int,
    val nuclideTypeCode: Int
)