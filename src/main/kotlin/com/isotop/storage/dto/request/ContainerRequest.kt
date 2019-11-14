package com.isotop.storage.dto.request

class ContainerRequest(
    val containerChipher: String,
    val openSourceTypeCode: Int,
    val sourceDiametr: Double,
    val sourceHeight: Double,
    val sourceActivity: Double,
    val openSourceCount: Int,
    val storageCode: Int,
    var moutionType: Int?,
    var sourceTypeCode: Int?,
    var openSourceActivity: Double?,
    var openSourceRest: Int?,
    var containerCode: Int?

)