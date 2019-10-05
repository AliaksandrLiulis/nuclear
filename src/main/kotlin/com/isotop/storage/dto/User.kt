package com.isotop.storage.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class User(
    val email:String,
    val userPassword:String,
    val role:Role)