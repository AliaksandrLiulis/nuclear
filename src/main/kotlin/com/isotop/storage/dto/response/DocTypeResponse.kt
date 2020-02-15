package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListDocTypeDataResponse(
    val data: List<DocTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DocTypeResponse(
    @JsonProperty("doc_type_code")
    val docTypeCode: Int,
    @JsonProperty("doc_type_name")
    val docTypeName: String
)
