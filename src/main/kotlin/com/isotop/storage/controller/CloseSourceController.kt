package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.service.CloseSourceService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/closesource")
open class CloseSourceController (
    private val closeSourceService: CloseSourceService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllCloseSource(
        authentication: Authentication
    ): ListStorageDataResponse {
        return closeSourceService.getAllCloseSource()
    }
}