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
            USERS.USER_CODE.`as`("userId"),
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
            USERS.USER_CODE.`as`("userId"),
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

    open fun getUserById(userId: Int): List<UserResponse> {

        return dsl.select(
            USERS.USER_CODE.`as`("userId"),
            USERS.NAME,
            USERS.EMAIL,
            USERS.ROLE
        ).from(
            USERS
        ).where(
            USERS.USER_CODE.eq(userId)
        ).orderBy(USERS.NAME)
            .fetchInto(UserResponse::class.java)
    }

    open fun createUser(
        payload: UserCreateRequest
    ): Int {

        val insertValues = mapOf<Any, Any?>(
            USERS.NAME to payload.userName,
            USERS.PASSWORD to payload.userPassword,
            USERS.EMAIL to payload.userEmail
        )
        return dsl
            .insertInto(USERS)
            .set(insertValues)
            .returning(USERS.USER_CODE)
            .fetchOne()
            .getValue(USERS.USER_CODE)
    }

    open fun updatePassword(userName: String, newPassword: String): Int {

        val insertValues = mapOf<Any, Any?>(
            USERS.PASSWORD to newPassword
        )
        return dsl
            .update(USERS)
            .set(insertValues)
            .where(USERS.NAME.eq(userName))
            .returning(USERS.USER_CODE)
            .fetchOne()
            .getValue(USERS.USER_CODE)
    }

    open fun isExistUserByEmail(
        payload: String
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(USERS.USER_CODE)
                .from(USERS)
                .where(USERS.EMAIL.equalIgnoreCase(payload))
        )
    }

    open fun isExistUserById(
        userId: Int
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(USERS.USER_CODE)
                .from(USERS)
                .where(USERS.USER_CODE.eq(userId))
        )
    }

    open fun isExistUserByName(
        userName: String
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(USERS.USER_CODE)
                .from(USERS)
                .where(USERS.NAME.equalIgnoreCase(userName))
        )
    }

    open fun isExistUserByPasswordAndUserName(userName: String, oldPassword: String): Boolean {

        return dsl.fetchExists(
            DSL.select(USERS.USER_CODE)
                .from(USERS)
                .where(USERS.NAME.equalIgnoreCase(userName).and(USERS.PASSWORD.eq(oldPassword)))
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
                USERS.USER_CODE.eq(idUser)
            )
            .returning(USERS.USER_CODE)
            .fetchOne()
            .get(USERS.USER_CODE)
    }
}
