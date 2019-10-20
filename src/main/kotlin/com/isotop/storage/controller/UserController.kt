package com.isotop.storage.controller

import com.isotop.storage.dto.request.UpdateRoleUserByIdRequest
import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.ListUserDataResponse
import com.isotop.storage.dto.response.UserResponse
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
    open fun getUsers(): ListUserDataResponse {
        return userService.getUsers()
    }

    @GetMapping("/role", produces = ["application/json"])
    open fun getUserAuthentication(
        authentication: Authentication
    ): UserResponse {
        return userService.getUserAuthentication(authentication)
    }

    @PostMapping(produces = ["application/json"])
    open fun createUser(
        @RequestBody
        @Valid
        payload: UserCreateRequest
    ): UserResponse {
        return userService.createUser(payload)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    open fun updateUserRoleByNameOrEmail(
        authentication: Authentication,
        @RequestBody
        @Valid
        payload: UpdateRoleUserByIdRequest
    ): UserResponse {
        return userService.updateUserRoleByUserId(authentication,payload)
    }
}