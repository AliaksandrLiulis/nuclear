package com.isotop.storage.repository

import com.isotop.storage.dto.AddContainerRequest
import com.isotop.storage.dto.request.UpdateStorageRequest
import com.isotop.storage.dto.response.StorageResponse
import com.isotop.storage.jooq.Tables.*
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
open class StorageRepository(
    private val dsl: DSLContext
) {

    private val outOrgNameQuery = DSL.name("f").fields("icon_org_name", "moution_storage_code")
        .`as`(
            DSL.select(ORGS.ICON_ORG_NAME.`as`("icon_org_name"), MOUTIONS.STORAGE_CODE.`as`("moution_storage_code"))
                .from(STORAGES)
                .leftOuterJoin(MOUTIONS).on(
                    STORAGES.STORAGE_CODE.eq(MOUTIONS.STORAGE_CODE)
                        .and(MOUTIONS.MOUTION_TYPE.notIn(1, 2))
                )
                .leftOuterJoin(ORGS).on(MOUTIONS.ORG_CODE.eq(ORGS.ORG_CODE))
                .where(
                    STORAGES.COME_DATE.isNotNull
                )
        )

    open fun getAllFromStorage(): List<StorageResponse> {

        return dsl.with(outOrgNameQuery).select(
            ORGS.ORG_CODE,
            ORGS.ICON_ORG_NAME,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            MAKE_TYPES.MAKE_TYPE_NAME,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY,
            STORAGES.COME_DATE,
            STORAGES.LEAVE_DATE,
            STORAGES.STORAGE_CODE,
            outOrgNameQuery.field("icon_org_name").`as`("outIconOrgName"),
            STORAGES.OWNER_ORG_CODE.`as`("owner_org_code"),
            STORAGES.NUCLIDE_TYPE_CODE.`as`("nuclide_type_code"),
            STORAGES.MAKE_TYPE_CODE.`as`("make_type_code"),
            STORAGES.SOURCE_TYPE_CODE.`as`("source_type_code"),
            MOUTIONS.DOC_DATE.`as`("doc_date"),
            MOUTIONS.DOC_NUMBER.`as`("doc_number"),
            MOUTIONS.ACT_CODE.`as`("act_code"),
            MOUTIONS.DOC_TYPE_CODE.`as`("doc_type_code")
        ).from(
            STORAGES
        )
            .leftOuterJoin(MOUTIONS)
            .on(STORAGES.STORAGE_CODE.eq(MOUTIONS.STORAGE_CODE).and(MOUTIONS.MOUTION_TYPE.`in`(1, 2)))
            .leftOuterJoin(ORGS).on(MOUTIONS.ORG_CODE.eq(ORGS.ORG_CODE))
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .leftJoin(outOrgNameQuery).on(
                outOrgNameQuery.field("moution_storage_code", Int::class.java)
                    .eq(STORAGES.STORAGE_CODE)
            )
            .where(
                STORAGES.COME_DATE.isNotNull
            ).orderBy(STORAGES.COME_DATE.desc())
            .fetchInto(StorageResponse::class.java)
    }

    open fun addStorageNote(addContainerRequest: AddContainerRequest): Int? {

        val insertValues = mapOf<Any, Any?>(
            STORAGES.PASSPORT_NUMBER to addContainerRequest.passportNumber,
            STORAGES.SERIAL_NUMBER to addContainerRequest.serialNumber,
            STORAGES.MAKE_DATE to addContainerRequest.makeDate,
            STORAGES.COME_DATE to addContainerRequest.comeDate,
            STORAGES.ACTIVITY to addContainerRequest.activity,
            STORAGES.SOURCE_TYPE_CODE to addContainerRequest.sourceTypeCode,
            STORAGES.NUCLIDE_TYPE_CODE to addContainerRequest.nuclideTypeCode,
            STORAGES.MAKE_TYPE_CODE to addContainerRequest.makeTypeCode,
            STORAGES.OWNER_ORG_CODE to addContainerRequest.ownerOrgCode
        )

        return dsl
            .insertInto(STORAGES)
            .set(insertValues)
            .returning(STORAGES.STORAGE_CODE)
            ?.fetchOne()
            ?.getValue(STORAGES.STORAGE_CODE)
    }

    open fun updateStorageNote(updateStorageRequest: UpdateStorageRequest): StorageResponse {

        val updateValues = mapOf<Any, Any?>(
            STORAGES.PASSPORT_NUMBER to updateStorageRequest.passportNumber,
            STORAGES.SERIAL_NUMBER to updateStorageRequest.serialNumber,
            STORAGES.MAKE_DATE to updateStorageRequest.makeDate,
            STORAGES.COME_DATE to updateStorageRequest.comeDate,
            STORAGES.ACTIVITY to updateStorageRequest.activity,
            STORAGES.NUCLIDE_TYPE_CODE to updateStorageRequest.nuclideTypeCode,
            STORAGES.MAKE_TYPE_CODE to updateStorageRequest.makeTypeCode,
            STORAGES.OWNER_ORG_CODE to updateStorageRequest.ownerOrgCode
        )

        val storageId = dsl
            .update(STORAGES)
            .set(updateValues)
            .where(
                STORAGES.STORAGE_CODE.eq(updateStorageRequest.storageCode)
            )
            .returning(STORAGES.STORAGE_CODE)
            ?.fetchOne()
            ?.getValue(STORAGES.STORAGE_CODE)

        return getStorageNoteByIdStorage(storageId!!)[0]

    }

    open fun getStorageNoteByIdStorage(idStorage: Int): List<StorageResponse> {

        return dsl.with(outOrgNameQuery).select(
            ORGS.ORG_CODE,
            ORGS.ICON_ORG_NAME,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            MAKE_TYPES.MAKE_TYPE_NAME,
            STORAGES.PASSPORT_NUMBER,
            STORAGES.SERIAL_NUMBER,
            STORAGES.MAKE_DATE,
            STORAGES.ACTIVITY,
            STORAGES.COME_DATE,
            STORAGES.LEAVE_DATE,
            STORAGES.STORAGE_CODE,
            outOrgNameQuery.field("icon_org_name").`as`("outIconOrgName"),
            STORAGES.OWNER_ORG_CODE.`as`("owner_org_code"),
            STORAGES.NUCLIDE_TYPE_CODE.`as`("nuclide_type_code"),
            STORAGES.MAKE_TYPE_CODE.`as`("make_type_code"),
            STORAGES.SOURCE_TYPE_CODE.`as`("source_type_code"),
            MOUTIONS.DOC_DATE.`as`("doc_date"),
            MOUTIONS.DOC_NUMBER.`as`("doc_number"),
            MOUTIONS.ACT_CODE.`as`("act_code"),
            MOUTIONS.DOC_TYPE_CODE.`as`("doc_type_code")
        ).from(
            STORAGES
        )
            .leftOuterJoin(MOUTIONS)
            .on(STORAGES.STORAGE_CODE.eq(MOUTIONS.STORAGE_CODE).and(MOUTIONS.MOUTION_TYPE.`in`(1, 2)))
            .leftOuterJoin(ORGS).on(MOUTIONS.ORG_CODE.eq(ORGS.ORG_CODE))
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .leftJoin(outOrgNameQuery).on(
                outOrgNameQuery.field("moution_storage_code", Int::class.java)
                    .eq(STORAGES.STORAGE_CODE)
            )
            .where(
                STORAGES.COME_DATE.isNotNull
            ).and(STORAGES.STORAGE_CODE.eq(idStorage))
            .orderBy(STORAGES.COME_DATE.desc())
            .fetchInto(StorageResponse::class.java)
    }

    open fun isExistStorageNoteById(idStorage: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(STORAGES.SOURCE_TYPE_CODE)
                .from(STORAGES)
                .where(STORAGES.STORAGE_CODE.eq(idStorage))
        )
    }
}
