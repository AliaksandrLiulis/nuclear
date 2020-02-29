package com.isotop.storage.repository

import com.isotop.storage.dto.response.StatementListResponse
import com.isotop.storage.jooq.Tables.*
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
}
