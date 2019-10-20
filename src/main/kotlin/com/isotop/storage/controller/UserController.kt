package com.isotop.storage.controller

import com.isotop.storage.dto.request.UpdateRoleUserRequest
import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserCreateResponse
import com.isotop.storage.dto.response.UserCreateResponseData
import com.isotop.storage.dto.response.UserIdResponse
import com.isotop.storage.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
@Validated
open class UserController(
    private val userService: UserService
) {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = ["application/json"])
    open fun getUsers(): UserCreateResponseData {
        return userService.getUsers()
    }

    @GetMapping("/role", produces = ["application/json"])
    open fun getRole(
        authentication: Authentication
    ): UserCreateResponseData {
        return userService.getRole(authentication)
    }

    @PostMapping(produces = ["application/json"])
    open fun createUser(
        @RequestBody
        @Valid
        payload: UserCreateRequest
    ): UserCreateResponse {
        return userService.createUser(payload)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    open fun updateUserRoleByNameOrEmail(
        authentication: Authentication,
        @RequestBody
        @Valid
        payload: UpdateRoleUserRequest
    ): UserIdResponse {
        return userService.updateUserRole(authentication,payload)
    }
}