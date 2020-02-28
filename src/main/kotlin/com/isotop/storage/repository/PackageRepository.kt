package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.OpenSourceUsingRequest
import com.isotop.storage.dto.response.PackageCodesResponse
import com.isotop.storage.dto.response.PackageResponse
import com.isotop.storage.jooq.Tables.*
import org.jooq.Record1
import org.jooq.impl.DSL
import org.jooq.impl.DSL.sum
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
open class PackageRepository(
    @Autowired
    private val dsl: DefaultDSLContext
) {

    open fun getPackagesByStorageCode(storageCode: Int): List<PackageResponse> {

        return dsl.select(
            PACKAGES.PACKAGE_CODE,
            PACKAGES.OPEN_SOURCE_USING,
            PACKAGES.SOURCE_ACTIVITY,
            NUCLIDE_TYPES.NUCLIDE_TYPE,
            CONTAINERS.CONTAINER_CHIPHER,
            CONTAINERS.SOURCE_DIAMETR,
            CONTAINERS.SOURCE_HEIGHT,
            MAKE_TYPES.MAKE_TYPE_NAME
        )
            .from(
                PACKAGES
            )
            .leftOuterJoin(CONTAINERS).on(PACKAGES.CONTAINER_CODE.eq(CONTAINERS.CONTAINER_CODE))
            .leftOuterJoin(STORAGES).on(PACKAGES.STORAGE_CODE.eq(STORAGES.STORAGE_CODE))
            .leftOuterJoin(NUCLIDE_TYPES).on(STORAGES.NUCLIDE_TYPE_CODE.eq(NUCLIDE_TYPES.NUCLIDE_TYPE_CODE))
            .leftOuterJoin(MAKE_TYPES).on(STORAGES.MAKE_TYPE_CODE.eq(MAKE_TYPES.MAKE_TYPE_CODE))
            .where(
                PACKAGES.STORAGE_CODE.eq(storageCode)
            )
            .fetchInto(PackageResponse::class.java)
    }

    open fun getPackageByPackageCode(packageCode: Int): PackageCodesResponse {

        return dsl.select(
            PACKAGES.STORAGE_CODE,
            PACKAGES.CONTAINER_CODE,
            PACKAGES.OPEN_SOURCE_USING,
            PACKAGES.SOURCE_ACTIVITY
        )
            .from(
                PACKAGES
            )
            .where(
                PACKAGES.PACKAGE_CODE.eq(packageCode)
            )
            .fetchInto(PackageCodesResponse::class.java)[0]
    }

    open fun addPackage(payload: OpenSourceUsingRequest): Int? {

        val insertValues = mapOf<Any, Any?>(
            PACKAGES.STORAGE_CODE to payload.storageCode,
            PACKAGES.CONTAINER_CODE to payload.containerCode,
            PACKAGES.OPEN_SOURCE_USING to payload.openSourceUsing,
            PACKAGES.SOURCE_ACTIVITY to payload.sourceActivity * payload.openSourceUsing
        )

        return dsl
            .insertInto(PACKAGES)
            .set(insertValues)
            .returning(PACKAGES.PACKAGE_CODE)
            .fetchOne()
            .getValue(PACKAGES.PACKAGE_CODE)
    }

    open fun removePackage(packageCode: Int) {
        try {
            dsl.delete(
                PACKAGES
            ).where(
                PACKAGES.PACKAGE_CODE.eq(packageCode)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }


    open fun isExistPackageByPackageCode(packageCode: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(PACKAGES.PACKAGE_CODE)
                .from(PACKAGES)
                .where(PACKAGES.PACKAGE_CODE.eq(packageCode))
        )
    }

    open fun isExistPackageByStorageCode(storageCode: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(PACKAGES.STORAGE_CODE)
                .from(PACKAGES)
                .where(PACKAGES.STORAGE_CODE.eq(storageCode))
        )
    }

    open fun getCommonActivityFromPackageByStorageCode(storageCode: Int): MutableList<String>? {
        return dsl.select(
            sum(PACKAGES.SOURCE_ACTIVITY)
        ).from(PACKAGES)
            .where(PACKAGES.STORAGE_CODE.eq(storageCode))
            .fetch().map { record1: Record1<BigDecimal>? -> record1!![0].toString() }
    }
}
