package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class MakeTypeRequest(
    @JsonProperty("make_type_code")
    val makeTypeCode: Int,
    @JsonProperty("make_type_name")
    val makeTypeName: String?
)
