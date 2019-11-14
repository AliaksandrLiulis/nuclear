package com.isotop.storage.controller

import com.isotop.storage.dto.request.AddContainerRequest
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
    @GetMapping("/{containerCode}", produces = ["application/json"])
    open fun getContainerByContainerCode(
        @PathVariable(value = "containerCode")
        containerId: Int
    ): ContainerResponse {
        return containerService.getContainerByContainerCode(containerId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addContainer(
        @RequestBody
        payload: AddContainerRequest
    ):ContainerResponse {
        payload.moutionType = 1
        payload.sourceTypeCode = 2
        return containerService.addContainer(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping(produces = ["application/json"])
    open fun updateContainer(
        @RequestBody
        payload: AddContainerRequest
    ) {
        if (payload.moutionType == null) {
            payload.moutionType = 1
        }

        payload.sourceTypeCode = 2
        containerService.updateContainer(payload)
    }
}
