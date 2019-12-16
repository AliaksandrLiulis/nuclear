package com.isotop.storage.repository.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.ActFormatUpdateRequest
import com.isotop.storage.dto.response.ActFormatResponse
import com.isotop.storage.jooq.Tables
import com.isotop.storage.jooq.Tables.ACT_FORMATS
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class ActFormatRepository(
    private val dsl: DSLContext
) {

    open fun getActFormats(): List<ActFormatResponse> {
        return dsl.select(
            ACT_FORMATS.ACT_FORMAT_CODE,
            ACT_FORMATS.ACT_FORMAT_NAME,
            ACT_FORMATS.FIXED_STATE
        ).from(
            ACT_FORMATS
        ).fetchInto(ActFormatResponse::class.java)
    }

    open fun getActFormatById(actFormatId: Int): List<ActFormatResponse> {
        return dsl.select(
            ACT_FORMATS.ACT_FORMAT_CODE,
            ACT_FORMATS.ACT_FORMAT_NAME,
            ACT_FORMATS.FIXED_STATE
        ).from(
            ACT_FORMATS
        )
            .where(
                ACT_FORMATS.ACT_FORMAT_CODE.eq(actFormatId)
            ).fetchInto(ActFormatResponse::class.java)
    }

    open fun addActFormat(actFormatName: String): ActFormatResponse {

        val actFormatId = dsl.insertInto(ACT_FORMATS)
            .columns(
                ACT_FORMATS.ACT_FORMAT_NAME,
                ACT_FORMATS.FIXED_STATE
            )
            .values(
                actFormatName, 0
            ).onDuplicateKeyUpdate()
            .set(ACT_FORMATS.ACT_FORMAT_NAME, actFormatName)
            .set(ACT_FORMATS.FIXED_STATE, 0)
            .returning(ACT_FORMATS.ACT_FORMAT_CODE)
            ?.fetchOne()
            ?.getValue(Tables.DOC_TYPES.DOC_TYPE_CODE)

        return getActFormatById(actFormatId!!)[0]
    }

    open fun updateActFormat(payload: ActFormatUpdateRequest): ActFormatResponse {

        val updateValues = mapOf<Any, Any?>(
            ACT_FORMATS.ACT_FORMAT_NAME to payload.actFormatName
        )

        val actFormatId = dsl
            .update(ACT_FORMATS)
            .set(updateValues)
            .where(
                ACT_FORMATS.ACT_FORMAT_CODE.eq(payload.actFormatCode)
            )
            .returning(ACT_FORMATS.ACT_FORMAT_CODE)
            ?.fetchOne()
            ?.getValue(ACT_FORMATS.ACT_FORMAT_CODE)

        return getActFormatById(actFormatId!!)[0]
    }

    open fun removeActFormat(id: Int) {
        try {
            dsl.delete(
                ACT_FORMATS
            ).where(
                ACT_FORMATS.ACT_FORMAT_CODE.eq(id)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistActFormatById(typeId: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(ACT_FORMATS.ACT_FORMAT_CODE)
                .from(ACT_FORMATS)
                .where(ACT_FORMATS.ACT_FORMAT_CODE.eq(typeId))
        )
    }
}