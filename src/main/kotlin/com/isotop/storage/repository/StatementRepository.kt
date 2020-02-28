package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.StatementAddRequest
import com.isotop.storage.dto.request.StatementUpdateRequest
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

    open fun addStatement(statementRequest: StatementAddRequest): StatementResponse {

        val insertValues = mapOf<Any, Any?>(
            STATEMENTS.STATEMENT_TYPE to statementRequest.statementType,
            STATEMENTS.STATEMENT_DATE to statementRequest.statementDate,
            STATEMENTS.STATEMENT_NUMBER to statementRequest.statementNumber,
            STATEMENTS.STATEMENT_INT_NUMBER to statementRequest.statementIntNumber,
            STATEMENTS.STATEMENT_NOTE to statementRequest.statementNote,
            STATEMENTS.CONTAINS_URANIUM to statementRequest.containsUranium
        )

        val statementId = dsl.insertInto(STATEMENTS)
            .values(
                insertValues
            )
            .returning(STATEMENTS.STATEMENT_CODE)
            ?.fetchOne()
            ?.getValue(STATEMENTS.STATEMENT_CODE)

        return getStatementById(statementId!!)
    }

    open fun updateStatement(statementUpdateRequest: StatementUpdateRequest): StatementResponse {

        val updateValues = mapOf<Any, Any?>(
            STATEMENTS.STATEMENT_TYPE to statementUpdateRequest.statementType,
            STATEMENTS.STATEMENT_DATE to statementUpdateRequest.statementDate,
            STATEMENTS.STATEMENT_NUMBER to statementUpdateRequest.statementNumber,
            STATEMENTS.STATEMENT_INT_NUMBER to statementUpdateRequest.statementIntNumber,
            STATEMENTS.STATEMENT_NOTE to statementUpdateRequest.statementNote,
            STATEMENTS.CONTAINS_URANIUM to statementUpdateRequest.containsUranium
        )

        val statementId = dsl
            .update(STATEMENTS)
            .set(updateValues)
            .where(
                STATEMENTS.STATEMENT_CODE.eq(statementUpdateRequest.statementCode)
            )
            .returning(STATEMENTS.STATEMENT_CODE)
            ?.fetchOne()
            ?.getValue(STATEMENTS.STATEMENT_CODE)

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
