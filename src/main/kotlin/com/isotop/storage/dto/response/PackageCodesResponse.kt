package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PackageCodesResponse(
    @JsonProperty("package_code")
    val packageCode: Int,
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("container_code")
    val containerCode: Int,
    @JsonProperty("open_source_using")
    val openSourceUsing: Int,
    @JsonProperty("source_activity")
    val souceActivity: Double
)
