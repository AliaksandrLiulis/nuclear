package com.isotop.storage.repository

import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.jooq.Tables.NUCLIDE_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class NuclideRepository(
    private val dsl: DSLContext
) {

    open fun getNuclideTypes(): List<NuclideTypeResponse> {
        return dsl.select(
            NUCLIDE_TYPES.NUCLIDE_TYPE_CODE,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            NUCLIDE_TYPES.NUCLIDE_TYPE_NAME
        ).from(
            NUCLIDE_TYPES
        ).fetchInto(NuclideTypeResponse::class.java)
    }

    open fun getNuclideTypeById(typeId: Int): List<NuclideTypeResponse> {
        return dsl.select(
            NUCLIDE_TYPES.NUCLIDE_TYPE_CODE,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            NUCLIDE_TYPES.NUCLIDE_TYPE_NAME
        ).from(
            NUCLIDE_TYPES
        )
            .where(
                NUCLIDE_TYPES.NUCLIDE_TYPE_CODE.eq(typeId)
            ).fetchInto(NuclideTypeResponse::class.java)
    }

    open fun isExistNuclideType(typeId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)
                .from(NUCLIDE_TYPES)
                .where(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE.eq(typeId))
        )
    }
}