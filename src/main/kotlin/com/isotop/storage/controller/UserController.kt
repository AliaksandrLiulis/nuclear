package com.isotop.storage.controller

import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserCreateResponse
import com.isotop.storage.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
open class UserController(
    private val userService: UserService
) {


    @PostMapping( produces = ["application/json"])
    open fun createUser(
        @RequestBody
        request: UserCreateRequest
    ): UserCreateResponse {
        return userService.createUser(request)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    open fun getUser(
        aunt: Authentication
    ):String {
    return aunt.name

    }
}