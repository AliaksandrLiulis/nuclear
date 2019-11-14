package com.isotop.storage.repository

import com.isotop.storage.dto.request.AddContainerRequest
import com.isotop.storage.dto.response.ContainerResponse
import com.isotop.storage.jooq.Tables.CONTAINERS
import com.isotop.storage.jooq.Tables.OPEN_SOURCE_TYPES
import org.jooq.DSLContext
import org.jooq.Record1
import org.jooq.impl.DSL
import org.jooq.impl.DSL.sum
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
open class ContainerRepository(
    private val dsl: DSLContext
) {

    open fun getAllFromContainer(): List<ContainerResponse> {

        return dsl.select(
            CONTAINERS.CONTAINER_CODE,
            CONTAINERS.CONTAINER_CHIPHER,
            CONTAINERS.OPEN_SOURCE_TYPE_CODE,
            CONTAINERS.SOURCE_DIAMETR,
            CONTAINERS.SOURCE_HEIGHT,
            CONTAINERS.OPEN_SOURCE_ACTIVITY,
            CONTAINERS.OPEN_SOURCE_COUNT,
            CONTAINERS.OPEN_SOURCE_REST,
            CONTAINERS.STORAGE_CODE,
            CONTAINERS.SOURCE_ACTIVITY,
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_NAME
        ).from(
            CONTAINERS
        )
            .leftOuterJoin(OPEN_SOURCE_TYPES)
            .on(OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE.eq(CONTAINERS.OPEN_SOURCE_TYPE_CODE))
            .fetchInto(ContainerResponse::class.java)
    }

    open fun getContainerByStorageCode(containerCodeId: Int): ContainerResponse {
        return dsl.select(
            CONTAINERS.CONTAINER_CODE,
            CONTAINERS.CONTAINER_CHIPHER,
            CONTAINERS.OPEN_SOURCE_TYPE_CODE,
            CONTAINERS.SOURCE_DIAMETR,
            CONTAINERS.SOURCE_HEIGHT,
            CONTAINERS.OPEN_SOURCE_ACTIVITY,
            CONTAINERS.OPEN_SOURCE_COUNT,
            CONTAINERS.OPEN_SOURCE_REST,
            CONTAINERS.STORAGE_CODE,
            CONTAINERS.SOURCE_ACTIVITY,
            OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_NAME
        ).from(
            CONTAINERS
        )
            .leftOuterJoin(OPEN_SOURCE_TYPES)
            .on(OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE.eq(CONTAINERS.OPEN_SOURCE_TYPE_CODE))
            .where(
                CONTAINERS.CONTAINER_CODE.eq(containerCodeId)
            ).fetchInto(ContainerResponse::class.java)[0]
    }

    open fun addContainerAndGetCommonActivity(payload: AddContainerRequest): Int? {

        val insertValues = mapOf<Any, Any?>(
            CONTAINERS.CONTAINER_CHIPHER to payload.containerChipher,
            CONTAINERS.OPEN_SOURCE_TYPE_CODE to payload.openSourceTypeCode,
            CONTAINERS.SOURCE_DIAMETR to payload.sourceDiametr,
            CONTAINERS.SOURCE_HEIGHT to payload.sourceHeight,
            CONTAINERS.OPEN_SOURCE_ACTIVITY to payload.openSourceActivity,
            CONTAINERS.OPEN_SOURCE_COUNT to payload.openSourceCount,
            CONTAINERS.OPEN_SOURCE_REST to payload.openSourceRest,
            CONTAINERS.STORAGE_CODE to payload.storageCode,
            CONTAINERS.SOURCE_ACTIVITY to payload.sourceActivity
        )

        return dsl
            .insertInto(CONTAINERS)
            .set(insertValues)
            .returning(CONTAINERS.CONTAINER_CODE)
            ?.fetchOne()
            ?.getValue(CONTAINERS.CONTAINER_CODE)
    }

    open fun updateContainerAndGetCommonActivity(payload: AddContainerRequest): Int? {

        val updateValues = mapOf<Any, Any?>(
            CONTAINERS.CONTAINER_CODE to payload.containerCode,
            CONTAINERS.STORAGE_CODE to payload.storageCode,
            CONTAINERS.CONTAINER_CHIPHER to payload.containerChipher,
            CONTAINERS.OPEN_SOURCE_TYPE_CODE to payload.openSourceTypeCode,
            CONTAINERS.SOURCE_DIAMETR to payload.sourceDiametr,
            CONTAINERS.SOURCE_HEIGHT to payload.sourceHeight,
            CONTAINERS.OPEN_SOURCE_ACTIVITY to payload.openSourceActivity,
            CONTAINERS.OPEN_SOURCE_COUNT to payload.openSourceCount,
            CONTAINERS.OPEN_SOURCE_REST to payload.openSourceRest,
            CONTAINERS.STORAGE_CODE to payload.storageCode,
            CONTAINERS.SOURCE_ACTIVITY to payload.sourceActivity
        )

        return dsl
            .insertInto(CONTAINERS)
            .set(updateValues)
            .onConflict(CONTAINERS.CONTAINER_CODE, CONTAINERS.STORAGE_CODE)
            .doUpdate()
            .set(updateValues)
            .returning(CONTAINERS.CONTAINER_CODE)
            ?.fetchOne()
            ?.getValue(CONTAINERS.CONTAINER_CODE)
    }

    open fun getCommonActivityByStorageCode(containerStorageCoe: Int): MutableList<String>? {

        return dsl.select(
            sum(CONTAINERS.OPEN_SOURCE_ACTIVITY * CONTAINERS.OPEN_SOURCE_COUNT)
        ).from(CONTAINERS)
            .where(CONTAINERS.STORAGE_CODE.eq(containerStorageCoe))
            .fetch().map { record1: Record1<BigDecimal>? -> record1!![0].toString() }
    }


    open fun isExistContainerByStorageCode(payload: Int): Boolean {

        return dsl.fetchExists(
            DSL.select(CONTAINERS.CONTAINER_CODE)
                .from(CONTAINERS)
                .where(CONTAINERS.STORAGE_CODE.eq(payload))
        )
    }

    open fun isExistContainerByContainerCode(containerCode: Int): Boolean {
        return dsl.fetchExists(
            DSL.select(CONTAINERS.CONTAINER_CODE)
                .from(CONTAINERS)
                .where(
                    CONTAINERS.CONTAINER_CODE.eq(containerCode)
                )
        )
    }


}