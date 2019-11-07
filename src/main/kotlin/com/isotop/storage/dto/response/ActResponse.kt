package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListActDataResponse(
    val data: List<ActResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActResponse(
    val actCode: Int,
    val actDate: LocalDate,
    val actNumber: String,
    val actIntNumber: Int?,
    val actFormatCode: Int,
    val actNote: String
)
