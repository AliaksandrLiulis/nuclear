package com.isotop.storage.controller

import com.isotop.storage.dto.request.OrganizationRequest
import com.isotop.storage.dto.response.ListOrganizationDataResponse
import com.isotop.storage.dto.response.OrganizationResponse
import com.isotop.storage.service.OrganizationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orgs")
open class OrganizationController(
    private val organizationService: OrganizationService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getOrganizations(): ListOrganizationDataResponse {
        return organizationService.getOrganizations()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addOrganizations(
        @RequestBody
        payload: OrganizationRequest
    ): OrganizationResponse {
        return organizationService.addOrganization(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping(produces = ["application/json"])
    open fun updateOrganizations(
        @RequestBody
        payload: OrganizationRequest
    ): OrganizationResponse {
        return organizationService.updateOrganization(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{organizationId}")
    open fun deleteOrganizations(
        @PathVariable(value = "organizationId")
        organizationId: Int
    ) {
        return organizationService.removeOrganization(organizationId)
    }
}
