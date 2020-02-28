package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.TransferRequest
import com.isotop.storage.dto.request.UpdateMotionRequest
import com.isotop.storage.dto.response.*
import com.isotop.storage.jooq.Tables.*
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

    open fun getActiveMotionEvent(): List<EventResponse> {

        return dsl.select(
            MAKE_TYPES.MAKE_TYPE_NAME,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            ORGS.SHORT_ORG_NAME,
            MOUTIONS.MOUTION_CODE,
            MOUTIONS.MOUTION_DATE,
            MOUTIONS.MOUTION_TYPE
        ).from(
            MOUTIONS
        )
            .leftOuterJoin(STORAGES).on(STORAGES.STORAGE_CODE.eq(MOUTIONS.STORAGE_CODE))
            .leftOuterJoin(ORGS).on(ORGS.ORG_CODE.eq(MOUTIONS.ORG_CODE))
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .where(
                MOUTIONS.HIDE_EVENT.eq(0)
            ).orderBy(MOUTIONS.MOUTION_DATE)
            .fetchInto(EventResponse::class.java)
    }

    open fun getMoutionTypeAndStorageCodeByMotionCodeId(moutionCode: Int): MotionTypeAndStorageCodeResponse {

        return dsl.select(
            MOUTIONS.MOUTION_TYPE,
            MOUTIONS.STORAGE_CODE
        ).from(
            MOUTIONS
        )
            .where(
                MOUTIONS.MOUTION_CODE.eq(moutionCode)
            ).fetchInto(MotionTypeAndStorageCodeResponse::class.java)[0]
    }

    open fun deactivateEvent(motionCodeId: Int) {

        val updateValues = mapOf<Any, Any?>(
            MOUTIONS.HIDE_EVENT to 1
        )

        dsl.update(MOUTIONS)
            .set(updateValues)
            .where(MOUTIONS.MOUTION_CODE.eq(motionCodeId))
            .execute()
    }

    open fun addMoution(
        moutionType: Int,
        moutionDate: LocalDate,
        orgCode: Int,
        storageCode: Int,
        docDate: LocalDate,
        docNumber: String,
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

    open fun updateMoutionByStorageCode(
        moutionDate: LocalDate,
        orgCode: Int,
        storageCode: Int,
        docDate: LocalDate,
        docNumber: String,
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

    open fun updateMoutionByMotionCode(
        payload: UpdateMotionRequest
    ): MoutionResponse {

        val updateValues = mapOf<Any, Any?>(
            MOUTIONS.MOUTION_DATE to payload.moutionDate,
            MOUTIONS.ORG_CODE to payload.orgCode,
            MOUTIONS.DOC_NUMBER to payload.docNumber,
            MOUTIONS.DOC_TYPE_CODE to payload.docTypeCode,
            MOUTIONS.DOC_DATE to payload.docDate
        )

        val moutionCode = dsl
            .update(MOUTIONS)
            .set(updateValues)
            .where(
                MOUTIONS.MOUTION_CODE.eq(payload.moutionCode)
            )
            .returning(MOUTIONS.MOUTION_CODE)
            ?.fetchOne()
            ?.getValue(MOUTIONS.MOUTION_CODE)

        return getMoutionsByMoutionCode(moutionCode!!)
    }

    open fun insertInMoutionNoteWhenGoToStorage(
        storageCode: Int
    ): Int? {

        val insertValues = mapOf<Any, Any?>(
            MOUTIONS.MOUTION_TYPE to 2,
            MOUTIONS.MOUTION_DATE to LocalDate.now(),
            MOUTIONS.ORG_CODE to 1,
            MOUTIONS.STORAGE_CODE to storageCode,
            MOUTIONS.HIDE_EVENT to 0
        )

        return dsl
            .insertInto(MOUTIONS)
            .set(insertValues)
            .returning(MOUTIONS.MOUTION_CODE)
            ?.fetchOne()
            ?.getValue(MOUTIONS.MOUTION_CODE)
    }

    open fun insertInMoutionNoteWhenTransfer(
        payload: TransferRequest
    ): Int? {

        val insertValues = mapOf<Any, Any?>(
            MOUTIONS.MOUTION_TYPE to payload.moutionType,
            MOUTIONS.MOUTION_DATE to payload.transferDate,
            MOUTIONS.ORG_CODE to payload.orgCode,
            MOUTIONS.STORAGE_CODE to payload.storageCode,
            MOUTIONS.DOC_DATE to payload.docDate,
            MOUTIONS.DOC_NUMBER to payload.docNumber,
            MOUTIONS.DOC_TYPE_CODE to payload.docTypeCode,
            MOUTIONS.HIDE_EVENT to 0,
            MOUTIONS.ACT_CODE to payload.actCode
        )

        return dsl
            .insertInto(MOUTIONS)
            .set(insertValues)
            .returning(MOUTIONS.MOUTION_CODE)
            ?.fetchOne()
            ?.getValue(MOUTIONS.MOUTION_CODE)
    }

    open fun removeMoutionsByStorageCode(storageCode: Int) {
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

    open fun removeMoutionsByMoutionCode(moutionCode: Int) {
        try {
            dsl.delete(
                MOUTIONS
            ).where(
                MOUTIONS.MOUTION_CODE.eq(moutionCode)
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

    open fun isExistMotionByMotionCode(idMoution: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(MOUTIONS.MOUTION_CODE)
                .from(MOUTIONS)
                .where(MOUTIONS.MOUTION_CODE.eq(idMoution))
        )
    }

    open fun getCountByStorageId(idStorage: Int): Int {
        return dsl.selectCount()
            .from(MOUTIONS)
            .where(MOUTIONS.STORAGE_CODE.eq(idStorage))
            ?.fetchOne(0, Int::class.java) ?: 0
    }

    open fun getMoutionTypes(): List<MoutionTypeResponse> {
        return dsl.select(
            MOUTION_TYPES.MOUTION_TYPE_CODE,
            MOUTION_TYPES.MOUTION_TYPE_NAME
        ).from(
            MOUTION_TYPES
        ).fetchInto(MoutionTypeResponse::class.java)
    }

    open fun getMoutionsByMoutionCode(motionCode: Int): MoutionResponse {
        return dsl.select(
            MOUTIONS.MOUTION_CODE,
            MOUTIONS.MOUTION_DATE,
            MOUTIONS.ACT_CODE,
            MOUTIONS.DOC_TYPE_CODE,
            MOUTIONS.DOC_NUMBER,
            MOUTIONS.DOC_DATE,
            MOUTIONS.MOUTION_TYPE,
            ORGS.ICON_ORG_NAME,
            ORGS.ORG_CODE,
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
            .where(MOUTIONS.MOUTION_CODE.eq(motionCode))
            .fetchInto(MoutionResponse::class.java)[0]
    }

    open fun getMoutions(): List<MoutionResponse> {
        return dsl.select(
            MOUTIONS.MOUTION_CODE,
            MOUTIONS.MOUTION_DATE,
            MOUTIONS.ACT_CODE,
            MOUTIONS.DOC_TYPE_CODE,
            MOUTIONS.DOC_NUMBER,
            MOUTIONS.DOC_DATE,
            MOUTIONS.MOUTION_TYPE,
            ORGS.ICON_ORG_NAME,
            ORGS.ORG_CODE,
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
            .orderBy(MOUTIONS.MOUTION_DATE.desc())
            .fetchInto(MoutionResponse::class.java)
    }
}
