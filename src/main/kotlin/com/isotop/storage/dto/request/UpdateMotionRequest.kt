package com.isotop.storage.dto.request

import java.time.LocalDate

data class UpdateMotionRequest(
    val moutionCode: Int,
    val moutionDate: LocalDate,
    val orgCode: Int,
    val docTypeCode: Int,
    val docNumber: Int,
    val docDate: LocalDate
)