package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.VarMainRequest
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.dto.response.VarMainResponse
import com.isotop.storage.jooq.Tables
import com.isotop.storage.jooq.tables.Statements.STATEMENTS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository


@Repository
open class StatementRepository(
    private val dsl: DSLContext
) {

    open fun getStatement(): List<StatementResponse> {
        return dsl.select(
            STATEMENTS.STATEMENT_CODE,
            STATEMENTS.STATEMENT_TYPE,
            STATEMENTS.STATEMENT_DATE,
            STATEMENTS.STATEMENT_NUMBER,
            STATEMENTS.STATEMENT_INT_NUMBER,
            STATEMENTS.STATEMENT_NOTE,
            STATEMENTS.CONTAINS_URANIUM
        ).from(
            STATEMENTS
        ).fetchInto(StatementResponse::class.java)
    }

    open fun getStatementById(statementCodeId: Int): StatementResponse {
        return dsl.select(
            STATEMENTS.STATEMENT_CODE,
            STATEMENTS.STATEMENT_TYPE,
            STATEMENTS.STATEMENT_DATE,
            STATEMENTS.STATEMENT_NUMBER,
            STATEMENTS.STATEMENT_INT_NUMBER,
            STATEMENTS.STATEMENT_NOTE,
            STATEMENTS.CONTAINS_URANIUM
        ).from(
            STATEMENTS
        )
            .where(
                STATEMENTS.STATEMENT_CODE.eq(statementCodeId)
            ).fetchOne().get(0, StatementResponse::class.java)
    }

    open fun addStatement(varMainRequest: VarMainRequest): StatementResponse {

        val statementId = dsl.insertInto(Tables.DOC_TYPES)
            .columns(
                Tables.VAR_MAIN.VAR_NAME,
                Tables.VAR_MAIN.VAR_VALUE
            )
            .values(
                varMainRequest.varName,
                varMainRequest.varValue
            ).onDuplicateKeyUpdate()
            .set(Tables.VAR_MAIN.VAR_NAME, Tables.VAR_MAIN.VAR_VALUE)
            .returning(Tables.VAR_MAIN.VAR_CODE)
            ?.fetchOne()
            ?.getValue(Tables.VAR_MAIN.VAR_CODE)

        return getStatementById(statementId!!)
    }

    open fun updateStatement(varMainRequest: VarMainRequest): StatementResponse {

        val updateValues = mapOf<Any, Any?>(
            Tables.VAR_MAIN.VAR_NAME to varMainRequest.varName,
            Tables.VAR_MAIN.VAR_VALUE to varMainRequest.varValue
        )

        val statementId = dsl
            .update(Tables.VAR_MAIN)
            .set(updateValues)
            .where(
                Tables.VAR_MAIN.VAR_CODE.eq(varMainRequest.varCode)
            )
            .returning(Tables.VAR_MAIN.VAR_CODE)
            ?.fetchOne()
            ?.getValue(Tables.VAR_MAIN.VAR_CODE)

        return getStatementById(statementId!!)

    }

    open fun removeStatement(statementId: Int) {
        try {
            dsl.delete(
                STATEMENTS
            ).where(
                STATEMENTS.STATEMENT_CODE.eq(statementId)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistStatementById(statementId: Int): Boolean {
        return dsl.fetchExists(
            dsl.select(STATEMENTS.STATEMENT_CODE)
                .from(STATEMENTS)
                .where(STATEMENTS.STATEMENT_CODE.eq(statementId))
        )
    }
}