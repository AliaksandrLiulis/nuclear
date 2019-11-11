package com.isotop.storage.repository

import com.isotop.storage.dto.response.ContainerResponse
import com.isotop.storage.jooq.Tables.CONTAINERS
import com.isotop.storage.jooq.Tables.OPEN_SOURCE_TYPES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

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
            .leftOuterJoin(OPEN_SOURCE_TYPES).on(OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE.eq(CONTAINERS.OPEN_SOURCE_TYPE_CODE))
            .fetchInto(ContainerResponse::class.java)
    }

    open fun getListContainerByStorageCode(storageCodeId: Int): List<ContainerResponse> {
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
            .leftOuterJoin(OPEN_SOURCE_TYPES).on(OPEN_SOURCE_TYPES.OPEN_SOURCE_TYPE_CODE.eq(CONTAINERS.OPEN_SOURCE_TYPE_CODE))
            .where(
                CONTAINERS.STORAGE_CODE.eq(storageCodeId)
            ).fetchInto(ContainerResponse::class.java)
    }

    open fun isExistContainerByStorageCode(payload: Int): Boolean {

        return dsl.fetchExists(
            DSL.select(CONTAINERS.CONTAINER_CODE)
                .from(CONTAINERS)
                .where(CONTAINERS.STORAGE_CODE.eq(payload))
        )
    }
}