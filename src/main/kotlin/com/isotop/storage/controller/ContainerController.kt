package com.isotop.storage.controller

import com.isotop.storage.dto.AddSourceRequest
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
    @PostMapping(produces = ["application/json"])
    open fun addContainer(
        @RequestBody
        payload: AddSourceRequest
    ): StorageResponse {
        payload.moutionType = 1
        payload.sourceTypeCode = 1
        return containerService.addContainer(payload)
    }
}
