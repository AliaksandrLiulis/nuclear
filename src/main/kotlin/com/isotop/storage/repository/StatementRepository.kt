package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.StatementAddRequest
import com.isotop.storage.dto.request.StatementUpdateRequest
import com.isotop.storage.dto.response.SourceFromStorageResponse
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.jooq.Tables.*
import com.isotop.storage.jooq.tables.Statements.STATEMENTS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sun.misc.MessageUtils.where

@Repository
open class StatementRepository(
    private val dsl: DSLContext
) {

    open fun getStatement(statementType: Int): List<StatementResponse> {
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
                STATEMENTS.STATEMENT_TYPE.eq(statementType)
            ).fetchInto(StatementResponse::class.java)
    }

    open fun getAllSourceStorageOnRegister(): List<SourceFromStorageResponse> {
        val sql = "SELECT A.passport_number, " +
                "A.storage_code, " +
                "A.serial_number, " +
                "A.make_date, " +
                "A.activity, " +
                "D.nuclide_type, " +
                "F.make_type_name " +
                "FROM storages A " +
                "left outer join statement_lists B On(A.storage_code=B.storage_code) " +
                "left outer join statements C On(B.statement_code=C.statement_code) " +
                "left outer join nuclide_types D on(A.nuclide_type_code=D.nuclide_type_code) " +
                "left outer join make_types F on(A.make_type_code=F.make_type_code) " +
                "left outer join (SELECT COUNT(*) StatementTypeCount, storage_code " +
                "FROM statement_lists group by storage_code) G on(A.storage_code=G.storage_code) " +
                "where A.come_date is not null and G.StatementTypeCount is null"

        return dsl.fetch(sql).into(SourceFromStorageResponse::class.java)
    }

    open fun getAllSourceStoragesFromRegister(): List<SourceFromStorageResponse> {
        val sql = "SELECT A.passport_number, " +
                "A.storage_code, " +
                "A.serial_number, " +
                "A.make_date, " +
                "A.activity, " +
                "D.nuclide_type, " +
                "F.make_type_name " +
                "FROM storages A " +
                "left outer join statement_lists B On(A.storage_code=B.storage_code) " +
                "left outer join statements C On(B.statement_code=C.statement_code) " +
                "left outer join nuclide_types D on(A.nuclide_type_code=D.nuclide_type_code) " +
                "left outer join make_types F on(A.make_type_code=F.make_type_code) " +
                "left outer join (SELECT COUNT(*) StatementTypeCount, storage_code " +
                "FROM statement_lists group by storage_code) G on(A.storage_code=G.storage_code) " +
                "where A.leave_date is not null and G.StatementTypeCount is null"

        return dsl.fetch(sql).into(SourceFromStorageResponse::class.java)

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
            ).fetchInto(StatementResponse::class.java)[0]
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
            .set(
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
