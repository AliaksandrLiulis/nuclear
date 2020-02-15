package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListPackagesDataResponse(
    val data: List<PackageResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PackageResponse(
    @JsonProperty("package_code")
    val packageCode: Int,
    @JsonProperty("open_source_using")
    val openSourceUsing: Int,
    @JsonProperty("source_activity")
    val sourceActivity: Double,
    @JsonProperty("nuclide_type")
    val nuclideType: String,
    @JsonProperty("container_chipher")
    val containerChipher: String,
    @JsonProperty("source_height")
    val sourceHeight: Double,
    @JsonProperty("source_diametr")
    val sourceDiametr: Double,
    @JsonProperty("make_type_name")
    val makeTypeName: String
)
