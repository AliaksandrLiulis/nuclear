package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListSourceTypeDataResponse (
    val data: List<SourceTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SourceTypeResponse (
    val sourceTypeCode: Int,
    val sourceType: String
)