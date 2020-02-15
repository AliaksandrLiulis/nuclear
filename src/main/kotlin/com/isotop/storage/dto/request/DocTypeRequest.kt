package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DocTypeRequest(
    @JsonProperty("doc_type_code")
    val docTypeCode: Int,
    @JsonProperty("doc_type_name")
    val docTypeName: String?
)
