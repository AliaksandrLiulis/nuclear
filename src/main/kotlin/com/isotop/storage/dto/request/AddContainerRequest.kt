package com.isotop.storage.dto.request

class AddContainerRequest(
    var containerСhipher: String,
    var openSourceTypeCode: Int,
    var sourceDiametr: Double,
    var sourceHeight: Double,
    var sourceActivity: Double,
    var openSourceCount: Int,
    val storageCode: Int,
    var moutionType: Int?,
    var sourceTypeCode: Int?,
    var openSourceActivity: Double?,
    var openSourceRest: Int?

)