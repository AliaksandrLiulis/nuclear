package com.isotop.storage.controller

import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserCreateResponse
import com.isotop.storage.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user", produces = ["application/json"])
open class UserController(
    private val userService: UserService
) {


    @PostMapping
    open fun createUser(
        @RequestBody
        request: UserCreateRequest
    ): UserCreateResponse {
        return userService.createUser(request)
    }
}