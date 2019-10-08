package com.isotop.storage.service

import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserCreateResponse
import com.isotop.storage.enums.UserRole
import com.isotop.storage.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserService(
    private val userRepository: UserRepository,
    private val encoder: BCryptPasswordEncoder
) {

    @Transactional
    open fun createUser(payload: UserCreateRequest): UserCreateResponse {

        payload.userPassword = encoder.encode(payload.userPassword)

        return UserCreateResponse(
            userId = userRepository.createUser(payload),
            name = payload.userName,
            email = payload.userEmail,
            role = UserRole.ROLE_UNAUTHORIZE
        )
    }
}