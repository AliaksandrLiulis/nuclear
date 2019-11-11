package com.isotop.storage.repository

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.OrganizationRequest
import com.isotop.storage.dto.response.OrganizationResponse
import com.isotop.storage.jooq.Tables.ORGS
import org.jooq.DSLContext
import org.jooq.impl.DSL
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
            ).orderBy(ORGS.ICON_ORG_NAME)
            .fetchInto(OrganizationResponse::class.java)
    }

    open fun getOrganizationById(typeId: Int): List<OrganizationResponse> {
        return dsl.select(
            ORGS.ORG_CODE,
            ORGS.ORG_NAME,
            ORGS.SHORT_ORG_NAME,
            ORGS.ICON_ORG_NAME,
            ORGS.ORG_ADDRESS
        ).from(
            ORGS
        )
            .where(
                ORGS.ORG_CODE.eq(typeId)
            ).fetchInto(OrganizationResponse::class.java)
    }

    open fun addOrganization(organizationRequest: OrganizationRequest): OrganizationResponse {

        val orgId = dsl.insertInto(ORGS)
            .columns(
                ORGS.ORG_NAME,
                ORGS.SHORT_ORG_NAME,
                ORGS.ICON_ORG_NAME,
                ORGS.ORG_ADDRESS
            )
            .values(
                organizationRequest.orgName,
                organizationRequest.shortOrgName,
                organizationRequest.iconOrgName,
                organizationRequest.orgAddress
            ).onDuplicateKeyUpdate()
            .set(ORGS.ORG_NAME, organizationRequest.orgName)
            .set(ORGS.SHORT_ORG_NAME, organizationRequest.shortOrgName)
            .set(ORGS.ICON_ORG_NAME, organizationRequest.iconOrgName)
            .set(ORGS.ORG_ADDRESS, organizationRequest.orgAddress)
            .returning(ORGS.ORG_CODE)
            ?.fetchOne()
            ?.getValue(ORGS.ORG_CODE)

        return getOrganizationById(orgId!!)[0]
    }

    open fun updateOrganization(organizationRequest: OrganizationRequest): OrganizationResponse {

        val updateValues = mapOf<Any, Any?>(
            ORGS.ORG_NAME to organizationRequest.orgName,
            ORGS.SHORT_ORG_NAME to organizationRequest.shortOrgName,
            ORGS.ICON_ORG_NAME to organizationRequest.iconOrgName,
            ORGS.ORG_ADDRESS to organizationRequest.orgAddress
        )

        val orgId = dsl
            .update(ORGS)
            .set(updateValues)
            .where(
                ORGS.ORG_CODE.eq(organizationRequest.orgCode)
            )
            .returning(ORGS.ORG_CODE)
            ?.fetchOne()
            ?.getValue(ORGS.ORG_CODE)

        return getOrganizationById(orgId!!)[0]
    }

    open fun removeOrganization(id: Int) {
        try {
            dsl.delete(
                ORGS
            ).where(
                ORGS.ORG_CODE.eq(id)
            ).execute()
        } catch (ex: Exception) {
            throw ValidationException(30)
        }
    }

    open fun isExistOrganizationByName(
        payload: String
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(ORGS.ORG_NAME)
                .from(ORGS)
                .where(ORGS.ORG_NAME.equalIgnoreCase(payload))
        )
    }

    open fun isExistOrganizationByShortOrgName(
        payload: String
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(ORGS.SHORT_ORG_NAME)
                .from(ORGS)
                .where(ORGS.SHORT_ORG_NAME.equalIgnoreCase(payload))
        )
    }

    open fun isExistOrganizationByIconOrgName(
        payload: String
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(ORGS.ICON_ORG_NAME)
                .from(ORGS)
                .where(ORGS.ICON_ORG_NAME.equalIgnoreCase(payload))
        )
    }

    open fun isExistOrganizationByOrgCode(
        payload: Int
    ): Boolean {

        return dsl.fetchExists(
            DSL.select(ORGS.ORG_CODE)
                .from(ORGS)
                .where(ORGS.ORG_CODE.eq(payload))
        )
    }
}
