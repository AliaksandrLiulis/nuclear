package com.isotop.storage.repository

import com.isotop.storage.dto.request.MakeSourceRequest
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
            MAKE_TYPES.MAKE_TYPE_CODE,
            MAKE_TYPES.MAKE_TYPE_NAME,
            NUCLIDE_TYPES.NUCLIDE_TYPE_CODE,
            NUCLIDE_TYPES.NUCLIDE_TYPE
        )
            .from(
                STORAGES
            )
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .where(
                STORAGES.MAKE_DATE.isNull
            ).orderBy(STORAGES.COME_DATE.desc())
            .fetchInto(MakeSourceResponse::class.java)
    }

    open fun getAllMakeSourcesBySourceId(sourceId:Int): List<MakeSourceResponse> {

        return dsl.select(
            STORAGES.STORAGE_CODE,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY,
            MAKE_TYPES.MAKE_TYPE_CODE,
            MAKE_TYPES.MAKE_TYPE_NAME,
            NUCLIDE_TYPES.NUCLIDE_TYPE_CODE,
            NUCLIDE_TYPES.NUCLIDE_TYPE
        )
            .from(
                STORAGES
            )
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .where(
                STORAGES.STORAGE_CODE.eq(sourceId)
            ).orderBy(STORAGES.STORAGE_CODE)
            .fetchInto(MakeSourceResponse::class.java)
    }

    open fun addMakeSource(payload: MakeSourceRequest): MakeSourceResponse {

        val insertValues = mapOf<Any, Any?>(
            STORAGES.PASSPORT_NUMBER to payload.passportNumber,
            STORAGES.SERIAL_NUMBER to payload.serialNumber,
            STORAGES.NUCLIDE_TYPE_CODE to payload.nuclideTypeCode,
            STORAGES.MAKE_TYPE_CODE to payload.makeTypeCode,
            STORAGES.ACTIVITY to 0
            )

        val sourceId =  dsl
            .insertInto(STORAGES)
            .set(insertValues)
            .returning(STORAGES.STORAGE_CODE)
            .fetchOne()
            .getValue(STORAGES.STORAGE_CODE)

        return getAllMakeSourcesBySourceId(sourceId)[0]
    }

    open fun updateMakeSource(payload: MakeSourceRequest): MakeSourceResponse {

        val updateValues = mapOf<Any, Any?>(
            STORAGES.PASSPORT_NUMBER to payload.passportNumber,
            STORAGES.SERIAL_NUMBER to payload.serialNumber,
            STORAGES.NUCLIDE_TYPE_CODE to payload.nuclideTypeCode,
            STORAGES.MAKE_TYPE_CODE to payload.makeTypeCode,
            STORAGES.ACTIVITY to 0
        )

        val sourceId =  dsl
            .update(STORAGES)
            .set(updateValues)
            .where(STORAGES.STORAGE_CODE.eq(payload.storageCode))
            .returning(STORAGES.STORAGE_CODE)
            .fetchOne()
            .getValue(STORAGES.STORAGE_CODE)

        return getAllMakeSourcesBySourceId(sourceId)[0]
    }
}
