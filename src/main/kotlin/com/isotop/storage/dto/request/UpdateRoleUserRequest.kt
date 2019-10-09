package com.isotop.storage.dto.request

import com.isotop.storage.jooq.enums.UserRole
import javax.validation.constraints.NotEmpty

class UpdateRoleUserRequest (
    @field:NotEmpty(message = "field identity must not be empty")
    val identity:String,
    val role:UserRole
)