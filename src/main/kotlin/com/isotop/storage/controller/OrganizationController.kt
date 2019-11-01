package com.isotop.storage.controller

import com.isotop.storage.dto.request.OrganizationRequest
import com.isotop.storage.dto.response.CodeResponse
import com.isotop.storage.dto.response.ListOrganizationDataResponse
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
    ): CodeResponse {
        return organizationService.addOrganization(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateOrganizations(
        @RequestBody
        payload: OrganizationRequest
    ): CodeResponse {
        return organizationService.updateOrganization(payload)
    }
}
