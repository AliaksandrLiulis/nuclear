package com.isotop.storage.dto.request

import java.time.LocalDate

data class ActRequest(
    val actCode: Int,
    val actDate: LocalDate,
    val actNumber: String,
    val actIntNumber: Int?,
    val actFormatCode: Int,
    val actNote: String
)