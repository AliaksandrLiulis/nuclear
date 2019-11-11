package com.isotop.storage.controller

import com.isotop.storage.dto.AddContainerRequest
import com.isotop.storage.dto.response.ContainerResponse
import com.isotop.storage.dto.response.ListContainerDataResponse
import com.isotop.storage.dto.response.StorageResponse
import com.isotop.storage.service.ContainerService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/containers")
open class ContainerController(
    private val containerService: ContainerService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllFromContainer(): ListContainerDataResponse {
        return containerService.getAllFromContainer()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{storageCode}", produces = ["application/json"])
    open fun getContainerByStorageCode(
        @PathVariable(value = "storageCode")
        containerId: Int
    ): ListContainerDataResponse {
        return containerService.getContainersByStorageCode(containerId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addContainer(
        @RequestBody
        payload: AddContainerRequest
    ): StorageResponse {
        payload.moutionType = 1
        payload.sourceTypeCode = 1
        return containerService.addContainer(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping(produces = ["application/json"])
    open fun updateContainer(
        @RequestBody
        payload: AddContainerRequest
    ): StorageResponse {
        return containerService.addContainer(payload)
    }
}
