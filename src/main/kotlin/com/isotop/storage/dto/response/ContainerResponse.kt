package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListContainerDataResponse(
    val data: List<ContainerResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContainerResponse(
    @JsonProperty("container_code")
    val containerCode: Int?,
    @JsonProperty("container_chipher")
    val containerChipher: String?,
    @JsonProperty("open_source_type_code")
    val openSourceTypeCode: Int?,
    @JsonProperty("source_diametr")
    val sourceDiametr: Double?,
    @JsonProperty("source_height")
    val sourceHeight: Double?,
    @JsonProperty("open_source_activity")
    val openSourceActivity: Double?,
    @JsonProperty("open_source_count")
    val openSourceCount: Int,
    @JsonProperty("open_source_rest")
    val openSourceRest: Int,
    @JsonProperty("storage_code")
    val storageCode: Int?,
    @JsonProperty("source_activity")
    val sourceActivity: Double?,
    @JsonProperty("open_source_type_name")
    val openSourceTypeName: String?
)
