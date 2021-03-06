package com.isotop.storage.repository.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.NuclideRequest
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.jooq.Tables.NUCLIDE_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class NuclideTypesRepository(
    private val dsl: DSLContext
) {

    open fun getNuclides(): List<NuclideTypeResponse> {
        return dsl.select(
            NUCLIDE_TYPES.NUCLIDE_TYPE_CODE,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            NUCLIDE_TYPES.NUCLIDE_TYPE_NAME
        ).from(
            NUCLIDE_TYPES
        ).orderBy(
            NUCLIDE_TYPES.NUCLIDE_TYPE_NAME
        ).fetchInto(NuclideTypeResponse::class.java)
    }

    open fun getNuclideById(typeId: Int): List<NuclideTypeResponse> {
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

    open fun addNuclide(nuclideRequest: NuclideRequest): NuclideTypeResponse {

        val nuclideId = dsl.insertInto(NUCLIDE_TYPES)
            .columns(
                NUCLIDE_TYPES.NUCLIDE_TYPE,
                NUCLIDE_TYPES.NUCLIDE_TYPE_NAME
            )
            .values(
                nuclideRequest.nuclideType,
                nuclideRequest.nuclideTypeName
            ).onDuplicateKeyUpdate()
            .set(NUCLIDE_TYPES.NUCLIDE_TYPE, nuclideRequest.nuclideType)
            .set(NUCLIDE_TYPES.NUCLIDE_TYPE_NAME, nuclideRequest.nuclideTypeName)
            .returning(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)
            ?.fetchOne()
            ?.getValue(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)

        return getNuclideById(nuclideId!!)[0]
    }

    open fun updateNuclide(nuclideRequest: NuclideRequest): NuclideTypeResponse {

        val updateValues = mapOf<Any, Any?>(
            NUCLIDE_TYPES.NUCLIDE_TYPE to nuclideRequest.nuclideType,
            NUCLIDE_TYPES.NUCLIDE_TYPE_NAME to nuclideRequest.nuclideTypeName
        )

        val nuclideId = dsl
            .update(NUCLIDE_TYPES)
            .set(updateValues)
            .where(
                NUCLIDE_TYPES.NUCLIDE_TYPE_CODE.eq(nuclideRequest.nuclideTypeCode)
            )
            .returning(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)
            ?.fetchOne()
            ?.getValue(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)

        return getNuclideById(nuclideId!!)[0]

    }

    open fun removeNuclide(id: Int) {
        try {
            dsl.delete(
                NUCLIDE_TYPES
            ).where(
                NUCLIDE_TYPES.NUCLIDE_TYPE_CODE.eq(id)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistNuclideTypeById(typeId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)
                .from(NUCLIDE_TYPES)
                .where(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE.eq(typeId))
        )
    }

    open fun isExistNuclideTypeByType(type: String): Boolean {
        return dsl.fetchExists(
            DSL.select(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)
                .from(NUCLIDE_TYPES)
                .where(NUCLIDE_TYPES.NUCLIDE_TYPE.equalIgnoreCase(type))
        )
    }

    open fun isExistNuclideTypeByTypeName(typeName: String): Boolean {
        return dsl.fetchExists(
            DSL.select(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE)
                .from(NUCLIDE_TYPES)
                .where(NUCLIDE_TYPES.NUCLIDE_TYPE_NAME.equalIgnoreCase(typeName))
        )
    }
}
