package com.isotop.storage.controller

import com.isotop.storage.dto.request.AddContainerToStorageRequest
import com.isotop.storage.dto.request.TransferRequest
import com.isotop.storage.dto.request.UpdateStorageRequest
import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.dto.response.StorageResponse
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
    ): StorageResponse {
        return storageService.updateStorage(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping("containers", produces = ["application/json"])
    open fun addContainerToStorage(
        @RequestBody
        payload: AddContainerToStorageRequest
    ): StorageResponse {
        payload.moutionType = 1
        payload.sourceTypeCode = 1
        return storageService.addContainerToStorage(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping("sources", produces = ["application/json"])
    open fun addSourceToStorage(
        @RequestBody
        payload: AddContainerToStorageRequest
    ): StorageResponse {
        payload.moutionType = 1
        payload.sourceTypeCode = 2
        return storageService.addSourceToStorage(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{storageId}")
    open fun removeStorageNote(
        @PathVariable(value = "storageId")
        storageId: Int
    ) {
        return storageService.removeStorageNote(storageId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping("/gotostorage/{storageCode}", produces = ["application/json"])
    open fun goToStorage(
        @PathVariable(value = "storageCode")
        storageCode: Int
    ) {
        return storageService.goToStorage(storageCode)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping("/transfer", produces = ["application/json"])
    open fun goToTransfer(
        @RequestBody
        payload: TransferRequest
    ) {
        return storageService.goToTransfer(payload)
    }
}
