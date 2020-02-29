package com.isotop.storage.dto.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class ChangePasswordRequest(
    @NotNull(message = "Password must be present")
    @Min(1000)
    val oldPassword: String,
    @NotNull(message = "Password must be present")
    @Min(1000)
    val newPassword: String
)