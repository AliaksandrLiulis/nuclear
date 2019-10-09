package com.isotop.storage.dto.request

import javax.validation.constraints.NotEmpty

data class UserCreateRequest(
    @field:NotEmpty(message = "user name must not be empty")
    val userName: String,
    @field:NotEmpty(message = "user mail must not be empty")
    val userEmail: String,
    @field:NotEmpty(message = "user password must not be empty")
    var userPassword :String
)
