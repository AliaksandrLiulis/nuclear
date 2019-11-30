package com.isotop.storage.repository

import com.isotop.storage.dto.request.OpenSourceUsingRequest
import com.isotop.storage.dto.response.PackageResponse
import com.isotop.storage.jooq.Tables.*
import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
open class PackageRepository(

    @Autowired
    val dsl: DefaultDSLContext
) {

    open fun getPackagesByStorageCode(storageCode: Int): List<PackageResponse> {

        return dsl.select(
            PACKAGES.PACKAGE_CODE,
            PACKAGES.OPEN_SOURCE_USING,
            PACKAGES.SOURCE_ACTIVITY,
            NUCLIDE_TYPES.NUCLIDE_TYPE_CODE,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            CONTAINERS.CONTAINER_CHIPHER,
            CONTAINERS.SOURCE_DIAMETR,
            CONTAINERS.SOURCE_HEIGHT
        )
            .from(
                PACKAGES
            )
            .leftOuterJoin(CONTAINERS).on(PACKAGES.CONTAINER_CODE.eq(CONTAINERS.CONTAINER_CODE))
            .leftOuterJoin(STORAGES).on(PACKAGES.STORAGE_CODE.eq(STORAGES.STORAGE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .where(
                PACKAGES.STORAGE_CODE.eq(storageCode)
            )
            .fetchInto(PackageResponse::class.java)
    }

    open fun addPackage(payload: OpenSourceUsingRequest): Int? {

        val insertValues = mapOf<Any, Any?>(
            PACKAGES.STORAGE_CODE to payload.storageCode,
            PACKAGES.CONTAINER_CODE to payload.containerCode,
            PACKAGES.OPEN_SOURCE_USING to payload.openSourceUsing,
            PACKAGES.SOURCE_ACTIVITY to payload.sourceActivity
        )

        return dsl
            .insertInto(PACKAGES)
            .set(insertValues)
            .returning(PACKAGES.PACKAGE_CODE)
            .fetchOne()
            .getValue(PACKAGES.PACKAGE_CODE)
    }


    open fun isExistPackageByStorageCode(StorageCode: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(PACKAGES.STORAGE_CODE)
                .from(PACKAGES)
                .where(PACKAGES.STORAGE_CODE.eq(StorageCode))
        )
    }
}
