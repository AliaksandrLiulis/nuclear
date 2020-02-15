package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class UpdateStorageRequest(
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("owner_code")
    val ownerOrgCode: Int,
    @JsonProperty("act_code")
    val actCode: Int,
    val activity: Double,
    @JsonProperty("come_date")
    val comeDate: LocalDate,
    @JsonProperty("doc_date")
    val docDate: LocalDate,
    @JsonProperty("doc_number")
    val docNumber: String,
    @JsonProperty("doc_type_code")
    val docTypeCode: Int,
    @JsonProperty("make_date")
    val makeDate: LocalDate,
    @JsonProperty("make_type_code")
    val makeTypeCode: Int,
    @JsonProperty("nuclide_type_code")
    val nuclideTypeCode: Int,
    @JsonProperty("org_code")
    val orgCode: Int,
    @JsonProperty("passport_number")
    val passportNumber: String,
    @JsonProperty("serial_number")
    val serialNumber: String
)
