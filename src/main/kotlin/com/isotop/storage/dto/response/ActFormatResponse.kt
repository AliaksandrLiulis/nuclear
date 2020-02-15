package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListActsFormatDataResponse(
    val data: List<ActFormatResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActFormatResponse (
    @JsonProperty("act_format_code")
    val actFormatCode: Int,
    @JsonProperty("act_format_name")
    val actFormatName: String,
    @JsonProperty("fixed_state")
    val fixedState: Int
)