package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListMoutionTypeDataResponse(
    val data: List<MoutionTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MoutionTypeResponse(
    val moutionTypeCode: Int,
    val moutionTypeName: String
)
