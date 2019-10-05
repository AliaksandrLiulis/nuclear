package com.isotop.storage.dto.request

data class UserCreateRequest(
    val userName: String,
    val userEmail: String,
    var userPassword :String
)
