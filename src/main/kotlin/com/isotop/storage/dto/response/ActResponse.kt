package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListActsDataResponse(
    val data: List<ActResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActResponse(
    @JsonProperty("act_code")
    val actCode: Int,
    @JsonProperty("act_date")
    val actDate: LocalDate,
    @JsonProperty("act_number")
    val actNumber: String,
    @JsonProperty("act_int_number")
    val actIntNumber: Int?,
    @JsonProperty("act_format_code")
    val actFormatCode: Int,
    @JsonProperty("act_note")
    val actNote: String,
    @JsonProperty("doc_type_name")
    val docTypeName: String?,
    @JsonProperty("passport_number")
    val passportNumber: String?,
    @JsonProperty("serial_number")
    val serialNumber: String?,
    @JsonProperty("make_date")
    val makeDate: LocalDate?,
    val activity: Double?
)
