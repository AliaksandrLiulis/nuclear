package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class UpdateMotionRequest(
    @JsonProperty("moution_code")
    val moutionCode: Int,
    @JsonProperty("moution_date")
    val moutionDate: LocalDate,
    @JsonProperty("org_code")
    val orgCode: Int,
    @JsonProperty("doc_type_code")
    val docTypeCode: Int,
    @JsonProperty("dock_number")
    val docNumber: Int,
    @JsonProperty("doc_date")
    val docDate: LocalDate
)