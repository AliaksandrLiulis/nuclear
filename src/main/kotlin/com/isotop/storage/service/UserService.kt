package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ResourceNotFoundException
import com.isotop.storage.config.exceptionHandlers.exception.ValidateException
import com.isotop.storage.dto.request.UpdateRoleUserByIdRequest
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
        val isExistName = userRepository.isExistUserByName(payload = payload.userName)
        val isExistEmail = userRepository.isExistUserByEmail(payload = payload.userEmail)

        if (isExistName) {
            throw ValidateException(2)
        }

        if (isExistEmail) {
            throw ValidateException(3)
        }

        payload.userPassword = encoder.encode(payload.userPassword)

        return UserResponse(
            userId = userRepository.createUser(payload),
            name = payload.userName,
            email = payload.userEmail,
            role = UserRole.ROLE_UNAUTHORIZE
        )
    }

//    @Transactional
//    open fun updateUserRoleByNameOrEmail(
//        authentication: Authentication,
//        payload: UpdateRoleUserByNameOrEmailRequest
//    ): UserIdResponse {
//        val user = userRepository.getUserByName(authentication.name)[0]
//        if (user.name.equals(payload.identity, ignoreCase = true) || user.email.equals(
//                payload.identity,
//                ignoreCase = true
//            )
//        ) {
//            throw ValidateException(4)
//        }
//
//        val result =
//            if (payload.identity.contains(getEmailSign)) {
//                userRepository.isExistUserByEmail(payload = payload.identity)
//            } else {
//                userRepository.isExistUserByName(payload = payload.identity)
//            }
//        if (result) {
//            val id = userRepository.updateUserRole(payload.identity, payload.role)
//            return if (id != 0) {
//                UserIdResponse(id, payload.role)
//            } else {
//                UserIdResponse(0, null)
//            }
//        } else {
//            throw ResourceNotFoundException(1)
//        }
//    }

    @Transactional
    open fun updateUserRoleByUserId(
        authentication: Authentication,
        payload: UpdateRoleUserByIdRequest
    ): UserResponse {
        val adminUser = userRepository.getUserByName(authentication.name)[0]
        if (userRepository.isExistUserById(payload.userId)) {
            if (adminUser.userId == payload.userId) {
                throw ValidateException(4)
            }
            val userId = userRepository.updateUserRoleByUserId(payload.userId, payload.role)
            return userRepository.getUserById(userId)[0]
        } else {
            throw ResourceNotFoundException(1)
        }
    }
}
