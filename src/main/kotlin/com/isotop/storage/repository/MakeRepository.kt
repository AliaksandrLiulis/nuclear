package com.isotop.storage.repository

import com.isotop.storage.dto.response.MakeTypeResponse
import com.isotop.storage.jooq.Tables.MAKE_TYPES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class MakeRepository (
    private val dsl: DSLContext
) {

    open fun getMakeTypes(): List<MakeTypeResponse> {
        return dsl.select(
            MAKE_TYPES.MAKE_TYPE_CODE,
            MAKE_TYPES.MAKE_TYPE_NAME
        ).from(
            MAKE_TYPES
        ).fetchInto(MakeTypeResponse::class.java)
    }
}
