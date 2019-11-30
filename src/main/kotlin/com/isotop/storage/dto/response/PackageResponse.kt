package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListPackagesDataResponse(
    val data: List<PackageResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PackageResponse(
    val openSourceUsing: Int,
    val sourceActivity: Double,
    val nuclideType: String,
    val containerChipher: String,
    val sourceDiametr: Double,
    val makeTypeName: Double
)