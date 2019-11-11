package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListContainerDataResponse(
    private val data: List<ContainerResponse>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContainerResponse(
    private val containerCode: Int?,
    private val containerChipher: String?,
    private val openSourceTypeCode: Int?,
    private val sourceDiametr: Double?,
    private val sourceHeight: Double?,
    private val openSourceActivity: Double?,
    private val openSourceCount: Int?,
    private val openSourceRest: Int?,
    private val storageCode: Int?,
    private val sourceActivity: Int?
)