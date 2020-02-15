package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListMoutionTypeDataResponse(
    val data: List<MoutionTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MoutionTypeResponse(
    @JsonProperty("moution_type_code")
    val moutionTypeCode: Int,
    @JsonProperty("moution_type_name")
    val moutionTypeName: String
)
