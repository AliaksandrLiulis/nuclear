package com.isotop.storage.dto.request

import com.isotop.storage.jooq.enums.UserRole
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Validated
class UpdateRoleUserByIdRequest (
    @NotNull(message = "user id must be present")
    @Min(1)
    val userId:Int,
    val role: UserRole
)