package com.isotop.storage.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.isotop.storage.enums.UserRole

data class ListUserDataResponse(
    val data: List<UserResponse>
)

data class UserResponse(
    @JsonProperty("user_code")
    val userId: Int,
    val name: String,
    val email: String,
    val role: UserRole
)
