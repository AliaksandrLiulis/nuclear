package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListAvailableSourceDataResponse(
    val data: List<AvailableSourceResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AvailableSourceResponse (
    val containerChipher: String?,
    val openSourceRest: Int?,
    val sourceDiametr: Double?,
    val sourceHeight: Double?,
    val openSourceTypeName: String?,
    val comeDate: LocalDate?,
    val passportNumber: String?
)