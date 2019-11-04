package com.isotop.storage.controller

import com.isotop.storage.dto.request.UpdateStorageRequest
import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.dto.response.StorageMoutionResponse
import com.isotop.storage.service.StorageService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

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

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateStorage(
        @RequestBody
        payload: UpdateStorageRequest
    ): StorageMoutionResponse {
        return storageService.updateStorage(payload)
    }
}
