package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.OrganizationRequest
import com.isotop.storage.dto.response.CodeResponse
import com.isotop.storage.dto.response.ListOrganizationDataResponse
import com.isotop.storage.repository.OrganizationRepository
import org.springframework.stereotype.Service

@Service
open class OrganizationService(
    private val organizationRepository: OrganizationRepository
) {

    open fun getOrganizations(): ListOrganizationDataResponse {
        return ListOrganizationDataResponse(organizationRepository.getOrganizations())
    }

    open fun addOrganization(organizationRequest: OrganizationRequest): CodeResponse {
        validateAddOrganizationRequest(organizationRequest)
        return CodeResponse(
            organizationRepository.addOrganization(organizationRequest)
        )
    }

    open fun updateOrganization(organizationRequest: OrganizationRequest): CodeResponse {
        validateUpdateOrganizationRequest(organizationRequest)
        return CodeResponse(organizationRepository.updateOrganization(organizationRequest))
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