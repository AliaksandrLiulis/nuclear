package com.isotop.storage.repository.types

import com.isotop.storage.dto.response.SourceTypeResponse
import com.isotop.storage.jooq.Tables.SOURCE_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class SourceTypeRepository(
    private val dsl: DSLContext
) {

    open fun getSourceTypes(): List<SourceTypeResponse> {
        return dsl.select(
            SOURCE_TYPES.SOURCE_TYPE_CODE,
            SOURCE_TYPES.SOURCE_TYPE
        ).from(
            SOURCE_TYPES
        ).fetchInto(SourceTypeResponse::class.java)
    }

    open fun getSourceTypeById(typeId: Int): List<SourceTypeResponse> {
        return dsl.select(
            SOURCE_TYPES.SOURCE_TYPE_CODE,
            SOURCE_TYPES.SOURCE_TYPE
        ).from(
            SOURCE_TYPES
        )
            .where(
                SOURCE_TYPES.SOURCE_TYPE_CODE.eq(typeId)
            ).fetchInto(SourceTypeResponse::class.java)
    }

    open fun isExistSourceType(typeId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(SOURCE_TYPES.SOURCE_TYPE_CODE)
                .from(SOURCE_TYPES)
                .where(SOURCE_TYPES.SOURCE_TYPE_CODE.eq(typeId))
        )
    }
}
