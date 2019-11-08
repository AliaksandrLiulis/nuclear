package com.isotop.storage.repository.directory

import com.isotop.storage.dto.request.MakeTypeRequest
import com.isotop.storage.dto.response.MakeTypeResponse
import com.isotop.storage.jooq.Tables.MAKE_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class MakeTypesRepository(
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

    open fun addMakeType(makeTypeRequest: MakeTypeRequest): MakeTypeResponse {

        val makeId = dsl.insertInto(MAKE_TYPES)
            .columns(
                MAKE_TYPES.MAKE_TYPE_NAME
            )
            .values(
                makeTypeRequest.makeTypeName
            ).onDuplicateKeyUpdate()
            .set(MAKE_TYPES.MAKE_TYPE_NAME, makeTypeRequest.makeTypeName)
            .returning(MAKE_TYPES.MAKE_TYPE_CODE)
            ?.fetchOne()
            ?.getValue(MAKE_TYPES.MAKE_TYPE_CODE)

        return getMakeTypeById(makeId!!)[0]
    }

    open fun updateMakeType(makeTypeRequest: MakeTypeRequest): MakeTypeResponse {

        val updateValues = mapOf<Any, Any?>(
            MAKE_TYPES.MAKE_TYPE_NAME to makeTypeRequest.makeTypeName
        )

        val makeId = dsl
            .update(MAKE_TYPES)
            .set(updateValues)
            .where(
                MAKE_TYPES.MAKE_TYPE_CODE.eq(makeTypeRequest.makeTypeCode)
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
            DSL.select(MAKE_TYPES.MAKE_TYPE_CODE)
                .from(MAKE_TYPES)
                .where(MAKE_TYPES.MAKE_TYPE_NAME.equalIgnoreCase(type))
        )
    }
}
