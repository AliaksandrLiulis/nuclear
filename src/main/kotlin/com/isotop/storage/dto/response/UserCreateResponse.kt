package com.isotop.storage.dto.response

import com.isotop.storage.enums.UserRole

data class UserCreateResponseData(
    val data: List<UserCreateResponse>
)

data class UserCreateResponse(
    val userId: Int,
    val name: String,
    val email: String,
    val role: UserRole
)
