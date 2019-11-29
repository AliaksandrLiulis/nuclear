package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

data class ListMakeSourceDataResponse(

    val data: List<MakeSourceResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MakeSourceResponse (
    val storageCode: Int,
    val passportNumber: String?,
    val serialNumber: String?,
    val makeDate: LocalDate?,
    val activity: Double?,
    val makeTypeCode: Int?,
    val makeTypeName: String?,
    val nuclideTypeCode: Int?,
    val nuclideTypeName: String?
)
