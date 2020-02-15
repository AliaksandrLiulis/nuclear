package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ActFormatUpdateRequest (
    @JsonProperty("act_format_code")
    val actFormatCode: Int,
    @JsonProperty("act_format_name")
    val actFormatName: String
)