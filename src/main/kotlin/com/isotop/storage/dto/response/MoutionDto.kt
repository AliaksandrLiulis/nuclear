package com.isotop.storage.dto.response

import java.time.LocalDate

data class MoutionDto(
    val moutionCode: Int,
    val moutionType: Int,
    val moutionDate: LocalDate,
    val orgCode: Int,
    val storageCode: String,
    val docDate: LocalDate,
    val docNumber: String,
    val docTypeCode: Int,
    val actCode: Int,
    val hideEvent: Int
)
