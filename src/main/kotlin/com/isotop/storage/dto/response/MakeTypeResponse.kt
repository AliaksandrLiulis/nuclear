package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListMakeTypeDataResponse(
    val data: List<MakeTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MakeTypeResponse(
    @JsonProperty("make_type_code")
    val makeTypeCode: Int,
    @JsonProperty("make_type_name")
    val makeTypeName: String
)
