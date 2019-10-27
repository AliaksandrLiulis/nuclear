package com.isotop.storage.service

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
}