package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListOrganizationDataResponse
import com.isotop.storage.service.OrganizationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}