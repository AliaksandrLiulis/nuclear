package com.isotop.storage.repository

import com.isotop.storage.dto.response.MakeSourceResponse
import com.isotop.storage.jooq.Tables.*
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class MakeSourcesRepository(
    private val dsl: DSLContext
) {

    open fun getAllMakeSources(): List<MakeSourceResponse> {

        return dsl.select(
            STORAGES.STORAGE_CODE,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY,
            MAKE_TYPES.MAKE_TYPE_CODE.`as`("make_type_code"),
            MAKE_TYPES.MAKE_TYPE_NAME,
            NUCLIDE_TYPES.NUCLIDE_TYPE_CODE.`as`("nuclide_type_code"),
            NUCLIDE_TYPES.NUCLIDE_TYPE_NAME
        )
            .from(
                STORAGES
            )
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .where(
                STORAGES.MAKE_DATE.isNotNull
            ).orderBy(STORAGES.COME_DATE.desc())
            .fetchInto(MakeSourceResponse::class.java)
    }
}
