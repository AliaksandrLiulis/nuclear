package com.isotop.storage.controller

import com.isotop.storage.dto.request.UpdateRoleUserRequest
import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserCreateResponse
import com.isotop.storage.dto.response.UserIdResponse
import com.isotop.storage.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
open class UserController(
    private val userService: UserService
) {


    @PostMapping(produces = ["application/json"])
    open fun createUser(
        @RequestBody
        payload: UserCreateRequest
    ): UserCreateResponse {
        return userService.createUser(payload)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    open fun updateUserRoleByNameOrEmail(
        @RequestBody
        payload: UpdateRoleUserRequest
    ): UserIdResponse {
        return userService.updateUserRole(payload)
    }
}