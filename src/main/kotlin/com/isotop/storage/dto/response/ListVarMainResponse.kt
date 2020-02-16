package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListVarMainResponse(
    val data: List<VarMainResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VarMainResponse(
    @JsonProperty("var_code")
    val varCode: Int,
    @JsonProperty("var_name")
    val varName: String,
    @JsonProperty("var_value")
    val varValue: String
)
