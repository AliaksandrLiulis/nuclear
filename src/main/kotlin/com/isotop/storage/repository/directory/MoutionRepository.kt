package com.isotop.storage.repository.directory

import com.isotop.storage.dto.response.MoutionResponse
import com.isotop.storage.dto.response.MoutionTypeResponse
import com.isotop.storage.jooq.Tables.*
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class MoutionRepository (
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

    open fun getMoutions(): List<MoutionResponse> {
        return dsl.select(
            MOUTIONS.MOUTION_DATE,
            ORGS.ICON_ORG_NAME,
            MOUTION_TYPES.MOUTION_TYPE_NAME,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            MAKE_TYPES.MAKE_TYPE_NAME
        ).from(
            MOUTIONS
        )
            .leftOuterJoin(MOUTION_TYPES).on(MOUTIONS.MOUTION_TYPE.eq(MOUTION_TYPES.MOUTION_TYPE_CODE))
            .leftOuterJoin(ORGS).on(MOUTIONS.ORG_CODE.eq(ORGS.ORG_CODE))
            .leftOuterJoin(STORAGES).on(MOUTIONS.STORAGE_CODE.eq(STORAGES.STORAGE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .fetchInto(MoutionResponse::class.java)
    }
}
