package com.isotop.storage.repository.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.VarMainRequest
import com.isotop.storage.dto.response.VarMainResponse
import com.isotop.storage.jooq.Tables
import com.isotop.storage.jooq.Tables.VAR_MAIN
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class VarMainRepository(
    private val dsl: DSLContext
) {

    open fun getVarMain(): List<VarMainResponse> {
        return dsl.select(
            VAR_MAIN.VAR_CODE,
            VAR_MAIN.VAR_NAME,
            VAR_MAIN.VAR_VALUE
        ).from(
            VAR_MAIN
        ).fetchInto(VarMainResponse::class.java)
    }

    open fun getVarMainById(varCodeId: Int): List<VarMainResponse> {
        return dsl.select(
            VAR_MAIN.VAR_CODE,
            VAR_MAIN.VAR_NAME,
            VAR_MAIN.VAR_VALUE
        ).from(
            VAR_MAIN
        )
            .where(
                VAR_MAIN.VAR_CODE.eq(varCodeId)
            ).fetchInto(VarMainResponse::class.java)
    }

    open fun addVarMain(varMainRequest: VarMainRequest): VarMainResponse {

        val varMainId = dsl.insertInto(Tables.DOC_TYPES)
            .columns(
                VAR_MAIN.VAR_NAME,
                VAR_MAIN.VAR_VALUE
            )
            .values(
                varMainRequest.varName,
                varMainRequest.varValue
            ).onDuplicateKeyUpdate()
            .set(VAR_MAIN.VAR_NAME, VAR_MAIN.VAR_VALUE)
            .returning(VAR_MAIN.VAR_CODE)
            ?.fetchOne()
            ?.getValue(VAR_MAIN.VAR_CODE)

        return getVarMainById(varMainId!!)[0]
    }

    open fun updateVarMain(varMainRequest: VarMainRequest): VarMainResponse {

        val updateValues = mapOf<Any, Any?>(
            VAR_MAIN.VAR_NAME to varMainRequest.varName,
            VAR_MAIN.VAR_VALUE to varMainRequest.varValue
        )

        val varMainId = dsl
            .update(VAR_MAIN)
            .set(updateValues)
            .where(
                VAR_MAIN.VAR_CODE.eq(varMainRequest.varCode)
            )
            .returning(VAR_MAIN.VAR_CODE)
            ?.fetchOne()
            ?.getValue(VAR_MAIN.VAR_CODE)

        return getVarMainById(varMainId!!)[0]

    }

    open fun removeVarMain(codeId: Int) {
        try {
            dsl.delete(
                VAR_MAIN
            ).where(
                VAR_MAIN.VAR_CODE.eq(codeId)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistVarMainById(codeId: Int): Boolean {
        return dsl.fetchExists(
            dsl.select(VAR_MAIN.VAR_CODE)
                .from(VAR_MAIN)
                .where(VAR_MAIN.VAR_CODE.eq(codeId))
        )
    }

    open fun isExistVarMainByVarName(varName: String): Boolean {
        return dsl.fetchExists(
            dsl.select(VAR_MAIN.VAR_CODE)
                .from(VAR_MAIN)
                .where(VAR_MAIN.VAR_NAME.equalIgnoreCase(varName))
        )
    }
}