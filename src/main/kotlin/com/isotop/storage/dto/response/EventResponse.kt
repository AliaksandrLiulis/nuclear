package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListEventDataResponse(
    val data: List<EventResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EventResponse(
    val makeTypeName: String?,
    val passportNumber: String?,
    val serialNumber: String?,
    val shortOrgName: String,
    val moutionCode: Int?,
    val moutionDate: LocalDate?,
    val moutionType: Int?
)
