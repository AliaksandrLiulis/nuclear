package com.isotop.storage.repository

import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
open class QueryRepository(
    @Autowired
    private val dsl: DefaultDSLContext
) {

    open fun executeQuery(query: String): String {
        return dsl.fetch(query).formatJSON()
    }
}
