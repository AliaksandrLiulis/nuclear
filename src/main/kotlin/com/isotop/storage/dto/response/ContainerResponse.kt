package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListContainerDataResponse(
    val data: List<ContainerResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContainerResponse(
    val containerCode: Int?,
    val containerChipher: String?,
    val openSourceTypeCode: Int?,
    val sourceDiametr: Double?,
    val sourceHeight: Double?,
    val openSourceActivity: Double?,
    val openSourceCount: Int?,
    val openSourceRest: Int?,
    val storageCode: Int?,
    val sourceActivity: Double?
)