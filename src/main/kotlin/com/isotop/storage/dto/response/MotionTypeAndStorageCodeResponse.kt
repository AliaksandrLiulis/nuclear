package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MotionTypeAndStorageCodeResponse(
    @JsonProperty("moution_type")
    val moutionType: Int,
    @JsonProperty("storage_code")
    val storageCode: Int
)
