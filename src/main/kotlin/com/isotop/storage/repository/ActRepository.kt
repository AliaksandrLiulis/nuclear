package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.ActRequest
import com.isotop.storage.dto.response.ActResponse
import com.isotop.storage.jooq.Tables.*
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class ActRepository(
    private val dsl: DSLContext
) {

    open fun getActs(): List<ActResponse> {
        return dsl.select(
            ACTS.ACT_CODE,
            ACTS.ACT_DATE,
            ACTS.ACT_NUMBER,
            ACTS.ACT_INT_NUMBER,
            ACTS.ACT_FORMAT_CODE,
            ACTS.ACT_NOTE,
            DOC_TYPES.DOC_TYPE_NAME,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY
        ).from(
            ACTS
        )
            .leftOuterJoin(MOUTIONS).on(MOUTIONS.ACT_CODE.eq(ACTS.ACT_CODE))
            .leftOuterJoin(DOC_TYPES).on(MOUTIONS.DOC_TYPE_CODE.eq(DOC_TYPES.DOC_TYPE_CODE))
            .leftOuterJoin(STORAGES).on(MOUTIONS.STORAGE_CODE.eq(STORAGES.STORAGE_CODE))
            .orderBy(ACTS.ACT_DATE.desc())
            .fetchInto(ActResponse::class.java)
    }

    open fun getActById(actId: Int): List<ActResponse> {
        return dsl.select(
            ACTS.ACT_CODE,
            ACTS.ACT_DATE,
            ACTS.ACT_NUMBER,
            ACTS.ACT_INT_NUMBER,
            ACTS.ACT_FORMAT_CODE,
            ACTS.ACT_NOTE,
            DOC_TYPES.DOC_TYPE_NAME,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY
        ).from(
            ACTS
        )
            .leftOuterJoin(MOUTIONS).on(MOUTIONS.ACT_CODE.eq(ACTS.ACT_CODE))
            .leftOuterJoin(DOC_TYPES).on(MOUTIONS.DOC_TYPE_CODE.eq(DOC_TYPES.DOC_TYPE_CODE))
            .leftOuterJoin(STORAGES).on(MOUTIONS.STORAGE_CODE.eq(STORAGES.STORAGE_CODE))
            .where(
                ACTS.ACT_CODE.eq(actId)
            ).fetchInto(ActResponse::class.java)
    }

    open fun addAct(actRequest: ActRequest): ActResponse {

        val insertValues = mapOf<Any, Any?>(
            ACTS.ACT_DATE to actRequest.actDate,
            ACTS.ACT_NUMBER to actRequest.actNumber,
            ACTS.ACT_INT_NUMBER to actRequest.actIntNumber,
            ACTS.ACT_FORMAT_CODE to actRequest.actFormatCode,
            ACTS.ACT_NOTE to actRequest.actNote
        )

        val actCode = dsl
            .insertInto(ACTS)
            .set(insertValues)
            .returning(ACTS.ACT_CODE)
            ?.fetchOne()
            ?.getValue(ACTS.ACT_CODE)

        return getActById(actCode!!)[0]
    }

    open fun updateAct(actRequest: ActRequest): ActResponse {

        val updateValues = mapOf<Any, Any?>(
            ACTS.ACT_DATE to actRequest.actDate,
            ACTS.ACT_NUMBER to actRequest.actNumber,
            ACTS.ACT_INT_NUMBER to actRequest.actIntNumber,
            ACTS.ACT_FORMAT_CODE to actRequest.actFormatCode,
            ACTS.ACT_NOTE to actRequest.actNote
        )

        val actId = dsl
            .update(ACTS)
            .set(updateValues)
            .where(
                ACTS.ACT_CODE.eq(actRequest.actCode)
            )
            .returning(ACTS.ACT_CODE)
            ?.fetchOne()
            ?.getValue(ACTS.ACT_CODE)

        return getActById(actId!!)[0]

    }

    open fun removeActById(Actid: Int) {
        try {
            dsl.delete(
                ACTS
            ).where(
                ACTS.ACT_CODE.eq(Actid)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistActById(actId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(ACTS.ACT_CODE)
                .from(ACTS)
                .where(ACTS.ACT_CODE.eq(actId))
        )
    }
}
