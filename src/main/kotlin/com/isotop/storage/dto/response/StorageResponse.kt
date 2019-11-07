package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListStorageDataResponse(

    val data: List<StorageResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class StorageResponse(
    val storageCode: Int,
    val orgCode: Int,
    val iconOrgName: String,
    val passportNumber: String?,
    val serialNumber: String?,
    val makeDate: LocalDate?,
    val activity: Double?,
    val comeDate: LocalDate?,
    val outIconOrgName: String?,
    val leaveDate: LocalDate?,
    val ownerOrgCode: Int?,
    val nuclideTypeCode: Int?,
    val makeTypeCode: Int?,
    val sourceTypeCode: Int?,
    val docDate: LocalDate?,
    val docNumber: String?,
    val docTypeCode: Int?,
    val actCode: Int?
)
