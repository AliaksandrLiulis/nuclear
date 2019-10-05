package com.isotop.storage.repository

import com.isotop.storage.jooq.Tables.PACKAGES
import com.isotop.storage.jooq.Tables.USERS
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
open class NewRepository @Autowired constructor(

    val dsl: DefaultDSLContext

) {

    open fun getUserName(password:String)=

        dsl.select(
        ).
                from(
                    USERS.`as`("table")
                ).
                where(
                    USERS.PASSWORD.eq(password)
                ).fetchOne(USERS.USERNAME)



}