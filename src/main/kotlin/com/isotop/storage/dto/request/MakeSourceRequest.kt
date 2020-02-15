package com.isotop.storage.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class MakeSourceRequest(
    @JsonProperty("storage_code")
    val storageCode: Int?,
    @JsonProperty("passport_number")
    val passportNumber: String,
    @JsonProperty("serial_number")
    val serialNumber: String,
    @JsonProperty("make_type_code")
    val makeTypeCode: Int,
    @JsonProperty("nuclide_type_code")
    val nuclideTypeCode: Int,
    @JsonProperty("make_date")
    val makeDate: LocalDate
)