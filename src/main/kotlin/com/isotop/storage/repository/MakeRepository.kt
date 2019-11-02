package com.isotop.storage.repository

import com.isotop.storage.dto.request.MakeRequest
import com.isotop.storage.dto.response.MakeTypeResponse
import com.isotop.storage.jooq.Tables.MAKE_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class MakeRepository(
    private val dsl: DSLContext
) {

    open fun getMakeTypes(): List<MakeTypeResponse> {
        return dsl.select(
            MAKE_TYPES.MAKE_TYPE_CODE,
            MAKE_TYPES.MAKE_TYPE_NAME
        ).from(
            MAKE_TYPES
        ).orderBy(
            MAKE_TYPES.MAKE_TYPE_NAME
        )
            .fetchInto(MakeTypeResponse::class.java)
    }

    open fun getMakeTypeById(typeId: Int): List<MakeTypeResponse> {
        return dsl.select(
            MAKE_TYPES.MAKE_TYPE_CODE,
            MAKE_TYPES.MAKE_TYPE_NAME
        ).from(
            MAKE_TYPES
        )
            .where(
                MAKE_TYPES.MAKE_TYPE_CODE.eq(typeId)
            ).fetchInto(MakeTypeResponse::class.java)
    }

    open fun addMakeType(makeRequest: MakeRequest): MakeTypeResponse {

        val makeId = dsl.insertInto(MAKE_TYPES)
            .columns(
                MAKE_TYPES.MAKE_TYPE_NAME
            )
            .values(
                makeRequest.makeTypeName
            ).onDuplicateKeyUpdate()
            .set(MAKE_TYPES.MAKE_TYPE_NAME, makeRequest.makeTypeName)
            .returning(MAKE_TYPES.MAKE_TYPE_CODE)
            ?.fetchOne()
            ?.getValue(MAKE_TYPES.MAKE_TYPE_CODE)

        return getMakeTypeById(makeId!!)[0]
    }

    open fun updateMakeType(makeRequest: MakeRequest): MakeTypeResponse {

        val updateValues = mapOf<Any, Any?>(
            MAKE_TYPES.MAKE_TYPE_NAME to makeRequest.makeTypeName
        )

        val makeId = dsl
            .update(MAKE_TYPES)
            .set(updateValues)
            .where(
                MAKE_TYPES.MAKE_TYPE_CODE.eq(makeRequest.nmakeTypeCode)
            )
            .returning(MAKE_TYPES.MAKE_TYPE_CODE)
            ?.fetchOne()
            ?.getValue(MAKE_TYPES.MAKE_TYPE_CODE)

        return getMakeTypeById(makeId!!)[0]

    }

    open fun removeMakeType(id: Int) {

        dsl.delete(
            MAKE_TYPES
        ).where(
            MAKE_TYPES.MAKE_TYPE_CODE.eq(id)
        ).execute()
    }

    open fun isExistMakeTypeById(typeId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(MAKE_TYPES.MAKE_TYPE_CODE)
                .from(MAKE_TYPES)
                .where(MAKE_TYPES.MAKE_TYPE_CODE.eq(typeId))
        )
    }

    open fun isExistMakeTypeByName(type: String): Boolean {
        return dsl.fetchExists(
            DSL.select(MAKE_TYPES.MAKE_TYPE_NAME)
                .from(MAKE_TYPES)
                .where(MAKE_TYPES.MAKE_TYPE_NAME.equalIgnoreCase(type))
        )
    }

}
