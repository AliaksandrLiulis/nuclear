package com.isotop.storage.dto.request

import com.isotop.storage.jooq.enums.UserRole
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class UpdateRoleUserByIdRequest (
    @NotNull(message = "user id must be present")
    @Min(1)
    val userId:Int,
    val role: UserRole
)
