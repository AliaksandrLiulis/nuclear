package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.MoutionDto
import com.isotop.storage.jooq.Tables.MOUTIONS
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
open class MoutionRepository(
    private val dsl: DSLContext
) {

    open fun getMoutionById(idMoution: Int): List<MoutionDto> {

        return dsl.select(
            MOUTIONS.MOUTION_CODE,
            MOUTIONS.MOUTION_TYPE,
            MOUTIONS.MOUTION_DATE,
            MOUTIONS.ORG_CODE,
            MOUTIONS.STORAGE_CODE,
            MOUTIONS.DOC_DATE,
            MOUTIONS.DOC_NUMBER,
            MOUTIONS.DOC_TYPE_CODE,
            MOUTIONS.ACT_CODE,
            MOUTIONS.HIDE_EVENT
        ).from(
            MOUTIONS
        )
            .where(
                MOUTIONS.MOUTION_CODE.eq(idMoution)
            ).fetchInto(MoutionDto::class.java)
    }

    open fun addMoution(
        moutionType: Int,
        moutionDate: LocalDate,
        orgCode: Int,
        storageCode: Int,
        docDate: LocalDate,
        docNumber: Int,
        docTypeCode: Int,
        actCode: Int
    ): Int? {

        val insertValues = mapOf<Any, Any?>(
            MOUTIONS.MOUTION_TYPE to moutionType,
            MOUTIONS.MOUTION_DATE to moutionDate,
            MOUTIONS.ORG_CODE to orgCode,
            MOUTIONS.STORAGE_CODE to storageCode,
            MOUTIONS.DOC_DATE to docDate,
            MOUTIONS.DOC_NUMBER to docNumber,
            MOUTIONS.DOC_TYPE_CODE to docTypeCode,
            MOUTIONS.ACT_CODE to actCode,
            MOUTIONS.HIDE_EVENT to 0
        )

        return dsl
            .insertInto(MOUTIONS)
            .set(insertValues)
            .returning(MOUTIONS.MOUTION_CODE)
            ?.fetchOne()
            ?.getValue(MOUTIONS.MOUTION_CODE)
    }

    open fun updateMoution(
        moutionDate: LocalDate,
        orgCode: Int,
        storageCode: Int,
        docDate: LocalDate,
        docNumber: Int,
        docTypeCode: Int,
        actCode: Int
    ): MoutionDto {

        val updateValues = mapOf<Any, Any?>(
            MOUTIONS.MOUTION_DATE to moutionDate,
            MOUTIONS.ORG_CODE to orgCode,
            MOUTIONS.DOC_DATE to docDate,
            MOUTIONS.DOC_NUMBER to docNumber,
            MOUTIONS.DOC_TYPE_CODE to docTypeCode,
            MOUTIONS.ACT_CODE to actCode
        )

        val moutionId = dsl
            .update(MOUTIONS)
            .set(updateValues)
            .where(
                MOUTIONS.STORAGE_CODE.eq(storageCode)
            )
            .returning(MOUTIONS.MOUTION_CODE)
            ?.fetchOne()
            ?.getValue(MOUTIONS.MOUTION_CODE)

        return getMoutionById(moutionId!!)[0]
    }

    open fun removeMoutions(storageCode: Int) {
        try {
            dsl.delete(
                MOUTIONS
            ).where(
                MOUTIONS.STORAGE_CODE.eq(storageCode)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistMotionByStorageId(idStorage: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(MOUTIONS.STORAGE_CODE)
                .from(MOUTIONS)
                .where(MOUTIONS.STORAGE_CODE.eq(idStorage))
        )
    }

    open fun getCountByStorageId(idStorage: Int): Int {
        return dsl.selectCount()
            .from(MOUTIONS)
            .where(MOUTIONS.STORAGE_CODE.eq(idStorage))
            ?.fetchOne(0, Int::class.java) ?: 0
    }
}
