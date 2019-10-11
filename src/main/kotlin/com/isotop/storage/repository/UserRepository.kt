package com.isotop.storage.repository

import com.isotop.storage.constant.getEmailSign
import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserCreateResponse
import com.isotop.storage.jooq.Tables.USERS
import com.isotop.storage.jooq.enums.UserRole
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class UserRepository(
    private val dsl: DSLContext
) {

    open fun getUsers(): List<UserCreateResponse> {

        return dsl.select(
            USERS.USERCODE.`as`("userId"),
            USERS.NAME,
            USERS.EMAIL,
            USERS.ROLE
        ).from(
            USERS
        ).fetchInto(UserCreateResponse::class.java)
    }

    open fun getUserByName(name: String): List<UserCreateResponse> {

        return dsl.select(
            USERS.USERCODE.`as`("userId"),
            USERS.NAME,
            USERS.EMAIL,
            USERS.ROLE
        ).from(
            USERS
        ).where(
            USERS.NAME.eq(name)
        ).fetchInto(UserCreateResponse::class.java)
    }

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

    open fun isExistUserByEmail(
        payload: String
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(USERS.USERCODE)
                .from(USERS)
                .where(USERS.EMAIL.equalIgnoreCase(payload))
        )
    }

    open fun isExistUserByName(
        payload: String
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(USERS.USERCODE)
                .from(USERS)
                .where(USERS.NAME.equalIgnoreCase(payload))
        )
    }

    open fun updateUserRole(
        identity: String,
        role: UserRole
    ): Int? {

        return dsl
            .update(USERS)
            .set(USERS.ROLE, role)
            .where(
                if (identity.contains(getEmailSign)) {
                    USERS.EMAIL.equalIgnoreCase(identity)
                } else {
                    USERS.NAME.equalIgnoreCase(identity)
                }
            )
            .returning(USERS.USERCODE)
            .fetchOne()
            ?.get(USERS.USERCODE)
            ?.or(0)
    }
}

