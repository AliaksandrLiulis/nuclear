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
    open fun getAllOrganizations(): ListOrganizationDataResponse {
        return organizationService.getOrganizations()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addOrganizations(
        @RequestBody
        payload: OrganizationRequest
    ): OrganizationResponse {
        return organizationService.addOrganization(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateOrganizations(
        @RequestBody
        payload: OrganizationRequest
    ): OrganizationResponse {
        return organizationService.updateOrganization(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteOrganizations(
        @PathVariable(value = "id")
        id: Int
    ) {
        return organizationService.removeOrganization(id)
    }
}
