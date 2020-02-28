package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListStorageDataResponse(
    val data: List<StorageResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class StorageResponse(
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("org_code")
    val orgCode: Int,
    @JsonProperty("icon_org_name")
    val iconOrgName: String,
    @JsonProperty("nuclide_type")
    val nuclideType: String?,
    @JsonProperty("make_type_name")
    val makeTypeName: String?,
    @JsonProperty("passport_number")
    val passportNumber: String?,
    @JsonProperty("serial_number")
    val serialNumber: String?,
    @JsonProperty("make_date")
    val makeDate: LocalDate?,
    val activity: Double?,
    @JsonProperty("come_date")
    val comeDate: LocalDate?,
    @JsonProperty("out_icon_org_name")
    val outIconOrgName: String?,
    @JsonProperty("leave_date")
    val leaveDate: LocalDate?,
    @JsonProperty("owner_org_code")
    val ownerOrgCode: Int?,
    @JsonProperty("nuclide_type_code")
    val nuclideTypeCode: Int?,
    @JsonProperty("make_type_code")
    val makeTypeCode: Int?,
    @JsonProperty("source_type_code")
    val sourceTypeCode: Int?,
    @JsonProperty("doc_date")
    val docDate: LocalDate?,
    @JsonProperty("doc_number")
    val docNumber: String?,
    @JsonProperty("doc_type_code")
    val docTypeCode: Int?,
    @JsonProperty("act_code")
    val actCode: Int?
)
