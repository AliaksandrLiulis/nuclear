package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenSourceUsingRequest(
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("container_code")
    val containerCode: Int,
    @JsonProperty("open_source_using")
    val openSourceUsing: Int,
    @JsonProperty("source_activity")
    val sourceActivity: Double
)
