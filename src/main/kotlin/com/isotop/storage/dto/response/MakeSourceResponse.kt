package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class ListMakeSourceDataResponse(

    val data: List<MakeSourceResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MakeSourceResponse(
    @JsonProperty("storage_code")
    val storageCode: Int,
    @JsonProperty("passport_number")
    val passportNumber: String?,
    @JsonProperty("serial_number")
    val serialNumber: String?,
    @JsonProperty("make_date")
    val makeDate: LocalDate?,
    val activity: Double?,
    @JsonProperty("make_type_code")
    val makeTypeCode: Int?,
    @JsonProperty("make_type_name")
    val makeTypeName: String?,
    @JsonProperty("nuclide_type_code")
    val nuclideTypeCode: Int?,
    @JsonProperty("nuclide_type")
    val nuclideType: String?
)
