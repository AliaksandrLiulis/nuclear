package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListStorageDataResponse(

    val data: List<StorageResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class StorageResponse(
    val iconOrgName: String,
    val nuclideType: String?,
    val makeTypeName: String?,
    val passportNumber: String?,
    val serialNumber: String?,
    val makeDate: LocalDate?,
    val activity: Double?,
    val comeDate: LocalDate?,
    val outIconOrgName: String?,
    val leaveDate: LocalDate?
)