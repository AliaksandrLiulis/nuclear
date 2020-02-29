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

    open fun getAllSourceFromStorageOnRegister(): List<SourceFromStorageResponse> {
        return dsl.select(
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            MAKE_TYPES.MAKE_TYPE_NAME
        ).from(
            STORAGES
        )
            .leftOuterJoin(STATEMENT_LISTS).on(STORAGES.STORAGE_CODE.eq(STATEMENT_LISTS.STORAGE_CODE))
            .leftOuterJoin(STATEMENTS).on(STATEMENT_LISTS.STATEMENT_CODE.eq(STATEMENTS.STATEMENT_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .where(
                STATEMENTS.STATEMENT_TYPE.isNotNull.and(STORAGES.COME_DATE.isNotNull)
            ).fetchInto(SourceFromStorageResponse::class.java)
    }

//    open fun getAllSourceFromStoragesFromRegister(): List<SourceFromStorageResponse> {
//        return dsl.select(
//            STORAGES.PASSPORT_NUMBER,
//            STORAGES.SERIAL_NUMBER,
//            STORAGES.MAKE_DATE,
//            STORAGES.ACTIVITY,
//            NUCLIDE_TYPES.NUCLIDE_TYPE,
//            MAKE_TYPES.MAKE_TYPE_NAME
//        ).from(
//            STORAGES
//        )
//            .leftOuterJoin(STATEMENT_LISTS).on(STORAGES.STORAGE_CODE.eq(STATEMENT_LISTS.STORAGE_CODE))
//            .leftOuterJoin(STATEMENTS).on(STATEMENT_LISTS.STATEMENT_CODE.eq(STATEMENTS.STATEMENT_CODE))
//            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
//            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
//            .leftOuterJoin(
//                dsl.selectCount(
//                      STORAGES.STORAGE_CODE
//                )
//                    .from(
//                        STATEMENT_LISTS
//                    ).groupBy(STATEMENT_LISTS.STORAGE_CODE)
//
//            ).on(STORAGES.STORAGE_CODE)
//            .where(
//                STATEMENTS.STATEMENT_TYPE.isNotNull.and(STORAGES.COME_DATE.isNotNull)
//            ).fetchInto(SourceFromStorageResponse::class.java)
//    }
//
//    private final String GET_ALL_SOURCE_FROM_STORAGES_FROM_REGISTER =
//    SELECT * FROM storages A left outer join statement_lists B On(A.StorageCode=B.StorageCode)
//    left outer join statements C On(B.StatementCode=C.StatementCode)
//    left outer join nuclide_types D on(A.NuclideTypeCode=D.NuclideTypeCode)
//    left outer join make_types F on(A.MakeTypeCode=F.MakeTypeCode)
//    left outer join  (SELECT COUNT(*) StatementTypeCount, StorageCode FROM statement_lists group by StorageCode) G on(A.StorageCode=G.StorageCode) where A.LeaveDate is not null and G.StatementTypeCount='1'";


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
