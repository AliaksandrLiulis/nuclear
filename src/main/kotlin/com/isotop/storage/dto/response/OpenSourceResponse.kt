package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListOpenSourceTypeDataResponse (
    val data: List<OpenSourceTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OpenSourceTypeResponse (
    @JsonProperty("open_source_type_code")
    val openSourceTypeCode: Int,
    @JsonProperty("open_source_type_name")
    val openSourceTypeName: String,
    @JsonProperty("open_source_type_note")
    val openSourceTypeNote: String?
)
