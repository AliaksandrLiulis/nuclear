package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListMoutionDataResponse(
    val data: List<MoutionResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MoutionResponse(
    @JsonProperty("moution_code")
    val moutionCode: Int,
    @JsonProperty("moution_date")
    val moutionDate: LocalDate,
    @JsonProperty("moution_type")
    val moutionType: Int,
    @JsonProperty("act_code")
    val actCode: Int?,
    @JsonProperty("doc_type_code")
    val docTypeCode: Int?,
    @JsonProperty("doc_number")
    val docNumber: String?,
    @JsonProperty("doc_date")
    val docDate: LocalDate?,
    @JsonProperty("icon_org_name")
    val iconOrgName: String,
    @JsonProperty("org_code")
    val orgCode: Int,
    @JsonProperty("moution_type_name")
    val moutionTypeName: String,
    @JsonProperty("passport_number")
    val passportNumber: String,
    @JsonProperty("serial_number")
    val serialNumber: String,
    @JsonProperty("make_date")
    val makeDate: LocalDate,
    val activity: Double,
    @JsonProperty("nuclide_type")
    val nuclideType: String,
    @JsonProperty("make_type_name")
    val makeTypeName: String
)
