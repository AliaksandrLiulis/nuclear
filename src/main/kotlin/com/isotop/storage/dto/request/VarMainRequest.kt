package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class VarMainRequest(
    @JsonProperty("var_code")
    val varCode: Int = 0,
    @JsonProperty("var_name")
    val varName: String,
    @JsonProperty("var_value")
    val varValue: String
)
