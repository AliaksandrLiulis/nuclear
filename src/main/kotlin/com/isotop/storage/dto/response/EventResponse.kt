package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListEventDataResponse(
    val data: List<EventResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EventResponse(
    @JsonProperty("make_type_name")
    val makeTypeName: String?,
    @JsonProperty("passport_number")
    val passportNumber: String?,
    @JsonProperty("serial_number")
    val serialNumber: String?,
    @JsonProperty("short_org_name")
    val shortOrgName: String,
    @JsonProperty("moution_code")
    val moutionCode: Int?,
    @JsonProperty("moution_date")
    val moutionDate: LocalDate?,
    @JsonProperty("moution_type")
    val moutionType: Int?
)
