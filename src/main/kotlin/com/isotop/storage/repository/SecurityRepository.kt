package com.isotop.storage.repository

import com.isotop.storage.dto.response.UserCheckResponse
import com.isotop.storage.jooq.Tables.USERS
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
open class SecurityRepository(

    @Autowired
    val dsl: DefaultDSLContext
) {

    open fun getUserByEmail(payload: String): MutableList<UserCheckResponse> {
        return dsl.select(
            USERS.NAME,
            USERS.EMAIL,
            USERS.PASSWORD,
            USERS.ROLE
        ).from(
            USERS
        ).where(USERS.EMAIL.equalIgnoreCase(payload))
            .fetchInto(UserCheckResponse::class.java)


    }

    open fun getUserByName(payload: String): MutableList<UserCheckResponse> {
        return dsl.select(
            USERS.NAME,
            USERS.EMAIL,
            USERS.PASSWORD,
            USERS.ROLE
        ).from(
            USERS
        ).where(USERS.NAME.equalIgnoreCase(payload))
            .fetchInto(UserCheckResponse::class.java)


    }
}