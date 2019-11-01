package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.service.StorageService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/storages")
open class StorageController(
    private val storageService: StorageService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllStorage(
        authentication: Authentication
    ): ListStorageDataResponse {
        return storageService.getAllStorage()
    }
}
