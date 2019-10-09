package com.isotop.storage.dto.request

import javax.validation.constraints.NotEmpty

data class UserCreateRequest(
    @field:NotEmpty(message = "User name must not be empty")
    val userName: String,
    @field:NotEmpty(message = "User email must not be empty")
    val userEmail: String,
    @field:NotEmpty(message = "User password must not be empty")
    var userPassword :String
)
