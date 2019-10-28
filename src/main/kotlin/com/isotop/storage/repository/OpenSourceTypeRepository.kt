package com.isotop.storage.repository

import com.isotop.storage.dto.response.OpenSourceTypeResponse
import com.isotop.storage.jooq.Tables
import com.isotop.storage.jooq.Tables.OPEN_SOURCE_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class OpenSourceTypeRepository(
    private val dsl: DSLContext
) {

    open fun getOpenSourceTypes(): List<OpenSourceTypeResponse> {
        return dsl.select(
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE,
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_NAME,
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_NOTE
        ).from(
            OPEN_SOURCE_TYPES
        ).fetchInto(OpenSourceTypeResponse::class.java)
    }

    open fun getOpenSourceTypeById(typeId: Int): List<OpenSourceTypeResponse> {
        return dsl.select(
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE,
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_NAME,
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_NOTE
        ).from(
            Tables.OPEN_SOURCE_TYPES
        )
            .where(
                OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE.eq(typeId)
            ).fetchInto(OpenSourceTypeResponse::class.java)
    }

    open fun isExistOpenSourceType(typeId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE)
                .from(OPEN_SOURCE_TYPES)
                .where(OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE.eq(typeId))
        )
    }
}