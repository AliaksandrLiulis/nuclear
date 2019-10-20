package com.isotop.storage.repository

import com.isotop.storage.dto.request.UserCreateRequest
import com.isotop.storage.dto.response.UserResponse
import com.isotop.storage.jooq.Tables.USERS
import com.isotop.storage.jooq.enums.UserRole
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class UserRepository(
    private val dsl: DSLContext
) {

    open fun getUsers(): List<UserResponse> {

        return dsl.select(
            USERS.USERCODE.`as`("userId"),
            USERS.NAME,
            USERS.EMAIL,
            USERS.ROLE
        ).from(
            USERS
        ).orderBy(USERS.NAME)
            .fetchInto(UserResponse::class.java)
    }

    open fun getUserByName(name: String): List<UserResponse> {

        return dsl.select(
            USERS.USERCODE.`as`("userId"),
            USERS.NAME,
            USERS.EMAIL,
            USERS.ROLE
        ).from(
            USERS
        ).where(
            USERS.NAME.eq(name)
        ).orderBy(USERS.NAME)
            .fetchInto(UserResponse::class.java)
    }

    open fun getUserById(idUser: Int): List<UserResponse> {

        return dsl.select(
            USERS.USERCODE.`as`("userId"),
            USERS.NAME,
            USERS.EMAIL,
            USERS.ROLE
        ).from(
            USERS
        ).where(
            USERS.USERCODE.eq(idUser)
        ).orderBy(USERS.NAME)
            .fetchInto(UserResponse::class.java)
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

    open fun isExistUserById(
        idUser: Int
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(USERS.USERCODE)
                .from(USERS)
                .where(USERS.USERCODE.eq(idUser))
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

    open fun updateUserRoleByUserId(
        idUser: Int,
        role: UserRole
    ): Int {

        return dsl
            .update(USERS)
            .set(USERS.ROLE, role)
            .where(
                USERS.USERCODE.eq(idUser)
            )
            .returning(USERS.USERCODE)
            .fetchOne()
            .get(USERS.USERCODE)
    }
}
