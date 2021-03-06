package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.StatementAddRequest
import com.isotop.storage.dto.request.StatementListRequest
import com.isotop.storage.dto.response.StatementListResponse
import com.isotop.storage.dto.response.StatementResponse
import com.isotop.storage.jooq.Tables.*
import com.isotop.storage.jooq.tables.Statements
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class StatementListRepository(
    private val dsl: DSLContext
) {

    open fun getStatementList(statementCode: Int): List<StatementListResponse> {
        return dsl.select(
            STATEMENT_LISTS.STATEMENT_LIST_CODE,
            STATEMENT_LISTS.STATEMENT_CODE,
            STATEMENT_LISTS.STORAGE_CODE,
            STATEMENT_LISTS.STORAGE_LIFE_TIME,
            STATEMENT_LISTS.STORAGE_CATEGORY,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.ACTIVITY,
            STORAGES.COME_DATE,
            ORGS.ICON_ORG_NAME
        ).from(
            STATEMENT_LISTS
        )
            .leftOuterJoin(STORAGES).on(STATEMENT_LISTS.STORAGE_CODE.eq(STORAGES.STORAGE_CODE))
            .leftOuterJoin(MOUTIONS)
            .on(STORAGES.STORAGE_CODE.eq(MOUTIONS.STORAGE_CODE).and(MOUTIONS.MOUTION_TYPE.eq(1)))
            .leftOuterJoin(ORGS).on(MOUTIONS.ORG_CODE.eq(ORGS.ORG_CODE))
            .where(
                STATEMENT_LISTS.STATEMENT_CODE.eq(statementCode)
            ).fetchInto(StatementListResponse::class.java)
    }

    open fun getStatementListByStatementListCode(statementListCode: Int): List<StatementListResponse> {
        return dsl.select(
            STATEMENT_LISTS.STATEMENT_LIST_CODE,
            STATEMENT_LISTS.STATEMENT_CODE,
            STATEMENT_LISTS.STORAGE_CODE,
            STATEMENT_LISTS.STORAGE_LIFE_TIME,
            STATEMENT_LISTS.STORAGE_CATEGORY,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.ACTIVITY,
            STORAGES.COME_DATE,
            ORGS.ICON_ORG_NAME
        ).from(
            STATEMENT_LISTS
        )
            .leftOuterJoin(STORAGES).on(STATEMENT_LISTS.STORAGE_CODE.eq(STORAGES.STORAGE_CODE))
            .leftOuterJoin(MOUTIONS)
            .on(STORAGES.STORAGE_CODE.eq(MOUTIONS.STORAGE_CODE).and(MOUTIONS.MOUTION_TYPE.eq(1)))
            .leftOuterJoin(ORGS).on(MOUTIONS.ORG_CODE.eq(ORGS.ORG_CODE))
            .where(
                STATEMENT_LISTS.STATEMENT_LIST_CODE.eq(statementListCode)
            ).fetchInto(StatementListResponse::class.java)
    }

    open fun addStatementListNote(statementListRequest: StatementListRequest): Int {

        val insertValues = mapOf<Any, Any?>(
            STATEMENT_LISTS.STATEMENT_CODE to statementListRequest.statementCode,
            STATEMENT_LISTS.STORAGE_CODE to statementListRequest.storageCode
        )

        return dsl.insertInto(STATEMENT_LISTS)
            .set(
                insertValues
            )
            .returning(STATEMENT_LISTS.STATEMENT_LIST_CODE)
            ?.fetchOne()
            ?.getValue(STATEMENT_LISTS.STATEMENT_LIST_CODE)!!
    }

    open fun removeStatementNoteList(statementListCode: Int) {
        try {
            dsl.delete(
                STATEMENT_LISTS
            ).where(
                STATEMENT_LISTS.STATEMENT_LIST_CODE.eq(statementListCode)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistStatementNoteByListCode(statementListCode: Int): Boolean {
        return dsl.fetchExists(
            dsl.select(STATEMENT_LISTS.STATEMENT_LIST_CODE)
                .from(STATEMENT_LISTS)
                .where(STATEMENT_LISTS.STATEMENT_LIST_CODE.eq(statementListCode))
        )
    }
}
