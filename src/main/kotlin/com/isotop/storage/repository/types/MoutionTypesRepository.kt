package com.isotop.storage.repository.types

import com.isotop.storage.dto.response.MoutionTypeResponse
import com.isotop.storage.jooq.Tables.MOUTION_TYPES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class MoutionTypesRepository (
    private val dsl: DSLContext
) {

    open fun getMoutionTypes(): List<MoutionTypeResponse> {
        return dsl.select(
            MOUTION_TYPES.MOUTION_TYPE_CODE,
            MOUTION_TYPES.MOUTION_TYPE_NAME
        ).from(
            MOUTION_TYPES
        ).fetchInto(MoutionTypeResponse::class.java)
    }
}
