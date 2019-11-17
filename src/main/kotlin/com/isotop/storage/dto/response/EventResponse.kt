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
    val motionCode: Int?,
    val motionDate: LocalDate?,
    val motionType: Int?
//
//    val docDate: LocalDate?,
//    val docNumber: String?,
//    val docTypeCode: Int?,
//    val hideEvent: Int?,
//
//
//    val makeDate: LocalDate?,
//    val comeDate: LocalDate?,
//    val leaveDate: LocalDate?,
//    val activity: Double?,
//    val sourceTypeCode: Int?,
//    val makeTypeCode: Int?,
//    val ownerOrgCode: Int?,
//    val orgName: Int,
//
//    val iconOrgName: String,
//    val orgAdress: String,
//
//    val nuclideType: String?,
//    val nuclideName: Int?
)
