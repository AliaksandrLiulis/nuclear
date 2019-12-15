package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListMoutionDataResponse(
    val data: List<MoutionResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MoutionResponse (
    val moutionDate: LocalDate,
    val iconOrgName: String,
    val moutionTypeName: String,
    val passportNumber: String,
    val serialNumber: String,
    val makeDate: LocalDate,
    val activity: Double,
    val nuclideType: String,
    val makeTypeName: String
)
