package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListMakeTypeDataResponse(
    val data: List<MakeTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MakeTypeResponse (
    val makeTypeCode: Int,
    val makeTypeName: String
)