package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListSourceFromStorageResponse(
    val data: List<SourceFromStorageResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SourceFromStorageResponse(
    @JsonProperty("passport_number")
    val passportNumber: String,
    @JsonProperty("serial_number")
    val serialNumber: String,
    @JsonProperty("make_date")
    val makeDate: LocalDate,
    @JsonProperty("activity")
    val activity: Double,
    @JsonProperty("nuclide_type")
    val nuclideType: String,
    @JsonProperty("make_type_name")
    val makeTypeName: String,
    @JsonProperty("storage_code")
    val storageCode: Int
)
