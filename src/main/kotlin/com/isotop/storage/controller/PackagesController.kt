package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListPackagesDataResponse
import com.isotop.storage.service.PackagesService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/packages")
open class PackagesController(
    private val packagesService: PackagesService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{storageId}", produces = ["application/json"])
    open fun getPackagesByStorageId(
        @PathVariable(value = "storageId")
        storageId: Int
    ): ListPackagesDataResponse {
        return packagesService.getPackagesByStorageCode(storageId)
    }
}
