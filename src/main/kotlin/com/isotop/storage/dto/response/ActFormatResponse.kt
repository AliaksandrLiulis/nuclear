package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListActsFormatDataResponse(
    val data: List<ActFormatResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActFormatResponse (
    val actFormatCode: Int,
    val actFormatName: String,
    val fixedState: Int
)