package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.OrganizationRequest
import com.isotop.storage.dto.response.ListOrganizationDataResponse
import com.isotop.storage.dto.response.OrganizationResponse
import com.isotop.storage.repository.OrganizationRepository
import org.springframework.stereotype.Service

@Service
open class OrganizationService(
    private val organizationRepository: OrganizationRepository
) {

    open fun getOrganizations(): ListOrganizationDataResponse {
        return ListOrganizationDataResponse(organizationRepository.getOrganizations())
    }

    open fun addOrganization(organizationRequest: OrganizationRequest): OrganizationResponse {
        validateAddOrganizationRequest(organizationRequest)
        return organizationRepository.addOrganization(organizationRequest)
    }

    open fun updateOrganization(organizationRequest: OrganizationRequest): OrganizationResponse {
        validateUpdateOrganizationRequest(organizationRequest)
        return organizationRepository.updateOrganization(organizationRequest)
    }

    open fun removeOrganization(id: Int) {
        if (!organizationRepository.isExistOrganizationByOrgCode(id)) {
            throw ValidationException(8)
        }
        return organizationRepository.removeOrganization(id)
    }

    private fun validateAddOrganizationRequest(organizationRequest: OrganizationRequest) {
        if (organizationRequest.orgName.isNullOrBlank()
            || organizationRepository.isExistOrganizationByName(organizationRequest.orgName)
        ) {
            throw ValidationException(5)
        }
        if (organizationRequest.shortOrgName.isNullOrBlank()
            || organizationRepository.isExistOrganizationByShortOrgName(organizationRequest.shortOrgName)
        ) {
            throw ValidationException(6)
        }
        if (organizationRequest.iconOrgName.isNullOrBlank()
            || organizationRepository.isExistOrganizationByIconOrgName(organizationRequest.iconOrgName)
        ) {
            throw ValidationException(7)
        }
    }

    private fun validateUpdateOrganizationRequest(organizationRequest: OrganizationRequest) {
        if (!organizationRepository.isExistOrganizationByOrgCode(organizationRequest.orgCode)) {
            throw ValidationException(8)
        }
        if (organizationRequest.orgName.isNullOrBlank()
            || organizationRequest.shortOrgName.isNullOrBlank()
            || organizationRequest.iconOrgName.isNullOrBlank()
        ) {
            throw ValidationException(9)
        }
    }
}
