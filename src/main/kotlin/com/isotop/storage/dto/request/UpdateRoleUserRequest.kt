package com.isotop.storage.dto.request

import com.isotop.storage.jooq.enums.UserRole

class UpdateRoleUserRequest (
    val identity:String,
    val role:UserRole
)