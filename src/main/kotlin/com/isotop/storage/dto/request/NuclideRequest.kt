package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class NuclideRequest(
    @JsonProperty("nuclide_type_code")
    val nuclideTypeCode: Int,
    @JsonProperty("nuclide_type")
    val nuclideType: String?,
    @JsonProperty("nuclide_type_name")
    val nuclideTypeName: String?
)
