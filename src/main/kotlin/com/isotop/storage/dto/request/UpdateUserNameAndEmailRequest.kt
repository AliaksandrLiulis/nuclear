package com.isotop.storage.dto.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

open class UpdateUserNameAndEmailRequest (
    @NotNull(message = "name must be present")
    @Min(1)
    val name: String,
    @NotNull(message = "email must be present")
    @Min(1)
    val email: String
)