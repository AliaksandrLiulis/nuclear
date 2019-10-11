package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ResourceNotFoundException
import com.isotop.storage.config.exceptionHandlers.exception.ValidateException
import com.isotop.storage.constant.getEmailSign
import com.isotop.storage.dto.request.UpdateRoleUserRequest
import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserCreateResponse
import com.isotop.storage.dto.response.UserCreateResponseData
import com.isotop.storage.dto.response.UserIdResponse
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

    open fun getUsers(): UserCreateResponseData {
        return  UserCreateResponseData(userRepository.getUsers())
    }

    @Transactional
    open fun createUser(payload: UserCreateRequest): UserCreateResponse {
        val isExistName = userRepository.isExistUserByName(payload = payload.userName)
        val isExistEmail = userRepository.isExistUserByEmail(payload = payload.userEmail)

        if (isExistName){
            throw ValidateException(2)
        }

        if (isExistEmail){
            throw ValidateException(3)
        }

        payload.userPassword = encoder.encode(payload.userPassword)

        return UserCreateResponse(
            userId = userRepository.createUser(payload),
            name = payload.userName,
            email = payload.userEmail,
            role = UserRole.ROLE_UNAUTHORIZE
        )
    }

    @Transactional
    open fun updateUserRole(authentication: Authentication, payload: UpdateRoleUserRequest): UserIdResponse {
        val user = userRepository.getUserByName(authentication.name)[0]
        if (user.name.equals(payload.identity, ignoreCase = true) || user.email.equals(payload.identity, ignoreCase = true)){
            throw ValidateException(4)
        }

        val result =
        if (payload.identity.contains(getEmailSign)){
            userRepository.isExistUserByEmail(payload = payload.identity)
        }else{
            userRepository.isExistUserByName(payload = payload.identity)
        }
        if (result){
            val id = userRepository.updateUserRole(payload.identity, payload.role)
            return if (id != 0){
                UserIdResponse(id, payload.role)
            }else{
                UserIdResponse(0, null)
            }
        }else{
            throw ResourceNotFoundException(1)
        }
    }
}
