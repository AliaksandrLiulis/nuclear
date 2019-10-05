package com.isotop.storage.repository

import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.jooq.Tables.USERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class UserRepository(
    private val dsl: DSLContext
) {

    open fun createUser(
        payload: UserCreateRequest
    ): Int {

        val insertValues = mapOf<Any, Any?>(
            USERS.RIGHTCODE to 0,
            USERS.NAME to payload.userName,
            USERS.PASSWORD to payload.userPassword,
            USERS.AUTOSORT to 0,
            USERS.EMAIL to payload.userEmail
        )
        return dsl
            .insertInto(USERS)
            .set(insertValues)
            .returning(USERS.USERCODE)
            .fetchOne()
            .getValue(USERS.USERCODE)
    }
}

