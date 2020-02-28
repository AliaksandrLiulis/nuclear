package com.isotop.storage.dto.response

import com.isotop.storage.enums.UserRole

data class UserCheckResponse(
    val name: String,
    val email: String,
    val password: String,
    val role: UserRole
)
