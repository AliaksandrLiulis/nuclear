package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListContainerDataResponse
import com.isotop.storage.service.ContainerService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
