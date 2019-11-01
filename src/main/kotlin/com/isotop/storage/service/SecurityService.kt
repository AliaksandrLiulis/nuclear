package com.isotop.storage.service

import com.isotop.storage.constant.getEmailSign
import com.isotop.storage.dto.response.UserCheckResponse
import com.isotop.storage.repository.SecurityRepository
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service(value = "securityservice")
open class SecurityService(
    private val securityRepository: SecurityRepository
) : UserDetailsService {

    override fun loadUserByUsername(payload: String): UserDetails {
        try{
        val user = getUserByUniqueParams(payload)
        return org.springframework.security.core.userdetails.User(
            if (payload.contains(getEmailSign)){
                user.email
            }else{
                user.name
            },
            user.password, getAuthority(user)
        )
        }catch (ex:Exception){
            throw InternalAuthenticationServiceException("User unauthorized")
        }
    }

    private fun getAuthority(user: UserCheckResponse): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(user.role.toString()))
    }

    private fun getUserByUniqueParams(payload: String): UserCheckResponse {
        return when (payload.contains(getEmailSign)) {
            true -> securityRepository.getUserByEmail(payload)[0]
            false -> securityRepository.getUserByName(payload)[0]
        }
    }
}
