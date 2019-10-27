package com.isotop.storage.repository

import com.isotop.storage.dto.response.OrganizationResponse
import com.isotop.storage.jooq.Tables.ORGS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class OrganizationRepository(
    private val dsl: DSLContext
) {

    open fun getOrganizations(): List<OrganizationResponse> {

        return dsl.select(
            ORGS.ORG_CODE,
            ORGS.ORG_NAME,
            ORGS.SHORT_ORG_NAME,
            ORGS.ICON_ORG_NAME,
            ORGS.ORG_ADDRESS
        )
            .from(
                ORGS
            )
            .fetchInto(OrganizationResponse::class.java)
    }
}
