package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListDocTypeDataResponse(
    val data: List<DocTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DocTypeResponse(
    val docTypeCode: Int,
    val docTypeName: String
)
