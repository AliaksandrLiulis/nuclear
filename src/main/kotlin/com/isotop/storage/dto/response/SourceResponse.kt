package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListSourceTypeDataResponse (
    val data: List<SourceTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SourceTypeResponse (
    @JsonProperty("source_type_code")
    val sourceTypeCode: Int,
    @JsonProperty("source_type")
    val sourceType: String
)
