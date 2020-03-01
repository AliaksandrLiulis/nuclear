package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ResourceNotFoundException
import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.ChangePasswordRequest
import com.isotop.storage.dto.request.UpdateRoleUserByIdRequest
import com.isotop.storage.dto.request.UpdateUserNameAndEmailRequest
import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.ListUserDataResponse
import com.isotop.storage.dto.response.UserResponse
import com.isotop.storage.enums.UserRole
import com.isotop.storage.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserService(
    private val userRepository: UserRepository,
    private val encoder: BCryptPasswordEncoder
) {

    open fun getUsers(): ListUserDataResponse {
        return ListUserDataResponse(userRepository.getUsers())
    }

    open fun getUserAuthentication(authentication: Authentication): UserResponse {
        return ListUserDataResponse(userRepository.getUserByName(authentication.name))
            .data[0]
    }

    @Transactional
    open fun createUser(payload: UserCreateRequest): UserResponse {
        val isExistName = userRepository.isExistUserByName(userName = payload.userName)
        val isExistEmail = userRepository.isExistUserByEmail(payload = payload.userEmail)

        if (isExistName) {
            throw ValidationException(2)
        }

        if (isExistEmail) {
            throw ValidationException(3)
        }

        payload.userPassword = encoder.encode(payload.userPassword)

        return UserResponse(
            userId = userRepository.createUser(payload),
            name = payload.userName,
            email = payload.userEmail,
            role = UserRole.ROLE_UNAUTHORIZE
        )
    }

    @Transactional
    open fun updateUserRoleByUserId(
        authentication: Authentication,
        payload: UpdateRoleUserByIdRequest
    ): UserResponse {
        val adminUser = userRepository.getUserByName(authentication.name)[0]
        if (userRepository.isExistUserById(payload.userId)) {
            if (adminUser.userId == payload.userId) {
                throw ValidationException(4)
            }
            val userId = userRepository.updateUserRoleByUserId(payload.userId, payload.role)
            return userRepository.getUserById(userId)[0]
        } else {
            throw ResourceNotFoundException(1)
        }
    }

    open fun updateUserProfile(
        authentication: Authentication,
        payload: UpdateUserNameAndEmailRequest
    ): UserResponse {
        val currentUser = userRepository.getUserByName(authentication.name)[0]
        if (payload.name.isNotEmpty() || payload.email.isNotEmpty() || !userRepository.isExistUserByNameAndEmail(
                payload.name,
                payload.email
            )
        ) {
            userRepository.updateUserProfile(currentUser.name, payload.name, payload.email)
            return userRepository.getUserById(currentUser.userId)[0]
        } else {
            throw ValidationException(48)
        }
    }


    @Transactional
    open fun updateUserPassword(
        authentication: Authentication,
        payload: ChangePasswordRequest
    ) {
        val adminUser = userRepository.getUserByName(authentication.name)[0]
        val password = userRepository.getUserPasswordByUserName(adminUser.name)
        if (password != null || payload.newPassword.length < 4) {
            if (encoder.matches(payload.oldPassword, password)) {
                val newPassword = encoder.encode(payload.newPassword)
                userRepository.updatePassword(adminUser.name, newPassword)
            } else {
                throw ValidationException(48)
            }
        } else {
            throw ValidationException(48)
        }
    }
}
