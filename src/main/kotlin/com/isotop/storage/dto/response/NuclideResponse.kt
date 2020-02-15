package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListNuclideTypeDataResponse(
    val data: List<NuclideTypeResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class NuclideTypeResponse(
    @JsonProperty("nuclide_type_code")
    val nuclideTypeCode: Int,
    @JsonProperty("nuclide_type")
    val nuclideType: String,
    @JsonProperty("nuclide_type_name")
    val nuclideTypeName: String
)
