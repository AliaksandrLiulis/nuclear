package com.isotop.storage.controller

import com.isotop.storage.dto.request.ContainerRequest
import com.isotop.storage.dto.response.ContainerResponse
import com.isotop.storage.dto.response.ListContainerDataResponse
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
        return containerService.getContainerByStorageCode(containerId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addContainer(
        @RequestBody
        payload: ContainerRequest
    ): ContainerResponse {
        payload.moutionType = 1
        payload.sourceTypeCode = 2
        return containerService.addContainer(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping(produces = ["application/json"])
    open fun updateContainer(
        @RequestBody
        payload: ContainerRequest
    ): ContainerResponse {
        if (payload.moutionType == null) {
            payload.moutionType = 1
        }
        payload.sourceTypeCode = 2
        return containerService.updateContainer(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{containerId}")
    open fun deleteContainer(
        @PathVariable(value = "containerId")
        containerId: Int
    ) {
        return containerService.removeCotainer(containerId)
    }
}
