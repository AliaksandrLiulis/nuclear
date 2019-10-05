package com.isotop.storage.repository

import com.isotop.storage.jooq.Tables.USERS
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
open class Repository(

    @Autowired
    val dsl: DefaultDSLContext
) {

    open fun getUserPassword(userName: String): String {
        return dsl.select(
            USERS.PASSWORD
        ).from(
            USERS
        ).where(USERS.USERNAME.equalIgnoreCase(userName))
            .fetchOne(USERS.PASSWORD).toString()

    }
}