package by.delta.service.impl

import com.isotop.storage.dto.Role
import com.isotop.storage.dto.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
open class UserServiceImpl @Autowired constructor(
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(str: String): UserDetails {
        val users: List<User> = listOf(User("add", bCryptPasswordEncoder.encode("123"), Role.ROLE_ADMIN))
        var x = org.springframework.security.core.userdetails.User(
            users[0].email,
            users[0].userPassword, getAuthority(users[0])
        )
        return x
    }


    private fun getAuthority(user: User): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(user.role.toString()))
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }
}