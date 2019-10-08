package com.isotop.storage.dto.response

import com.isotop.storage.jooq.enums.UserRole

class UserIdResponse(
    val id: Int?,
    val role: UserRole?
)