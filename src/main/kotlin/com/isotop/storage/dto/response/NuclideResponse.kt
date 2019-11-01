package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListNuclideTypeDataResponse(
    val data: List<NuclideTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class NuclideTypeResponse(
    val nuclideTypeCode: Int,
    val nuclideType: String,
    val nuclideTypeName: String
)
