package com.isotop.storage.dto.request

import java.time.LocalDate

data class AddContainerToStorageRequest(
    var moutionType: Int?,
    var sourceTypeCode: Int?,
    val ownerOrgCode: Int,
    val actCode: Int,
    val activity: Double,
    val comeDate: LocalDate,
    val docDate: LocalDate,
    val docNumber: String,
    val docTypeCode: Int,
    val makeDate: LocalDate,
    val makeTypeCode: Int,
    val nuclideTypeCode: Int,
    val orgCode: Int,
    val passportNumber: String,
    val serialNumber: String
)