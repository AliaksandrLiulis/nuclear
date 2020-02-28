package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

class ContainerRequest(
    @JsonProperty("container_chipher")
    val containerChipher: String,
    @JsonProperty("open_source_type_code")
    val openSourceTypeCode: Int,
    @JsonProperty("source_diametr")
    val sourceDiametr: Double,
    @JsonProperty("source_height")
    val sourceHeight: Double,
    @JsonProperty("source_activity")
    val sourceActivity: Double,
    @JsonProperty("open_source_count")
    val openSourceCount: Int,
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("moution_type")
    var moutionType: Int?,
    @JsonProperty("source_type_code")
    var sourceTypeCode: Int?,
    @JsonProperty("open_source_activity")
    var openSourceActivity: Double?,
    @JsonProperty("open_source_rest")
    var openSourceRest: Int?,
    @JsonProperty("container_code")
    var containerCode: Int?
)
