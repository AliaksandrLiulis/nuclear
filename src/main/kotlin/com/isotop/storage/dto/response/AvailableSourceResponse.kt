package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListAvailableSourceDataResponse(
    val data: List<AvailableSourceResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AvailableSourceResponse (
    @JsonProperty("container_code")
    val containerCode: Int,
    @JsonProperty("container_chipher")
    val containerChipher: String?,
    @JsonProperty("open_source_rest")
    val openSourceRest: Int?,
    @JsonProperty("source_diametr")
    val sourceDiametr: Double?,
    @JsonProperty("source_height")
    val sourceHeight: Double?,
    @JsonProperty("open_source_type_name")
    val openSourceTypeName: String?,
    @JsonProperty("come_date")
    val comeDate: LocalDate?,
    @JsonProperty("passport_number")
    val passportNumber: String?,
    @JsonProperty("open_source_activity")
    val openSourceActivity: String?
)