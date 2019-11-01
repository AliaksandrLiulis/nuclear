package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListOpenSourceTypeDataResponse (
    val data: List<OpenSourceTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OpenSourceTypeResponse (
    val openSourceTypeCode: Int,
    val openSourceTypeName: String,
    val openSourceTypeNote: String?
)
