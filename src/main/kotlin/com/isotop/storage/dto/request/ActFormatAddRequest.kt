package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ActFormatAddRequest(
    @JsonProperty("act_format_name")
    val actFormatName: String
)