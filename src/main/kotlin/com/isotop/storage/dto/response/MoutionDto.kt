package com.isotop.storage.dto.response

import java.time.LocalDate

data class MoutionDto(
    val moutionCode: Int,
    val moutionType: Int,
    val moutionDate: LocalDate,
    val orgCode: Int,
    val storageCode: Int,
    val docDate: LocalDate,
    val docNumber: Int,
    val docTypeCode: Int,
    val actCode: Int,
    val hideEvent: Int
)