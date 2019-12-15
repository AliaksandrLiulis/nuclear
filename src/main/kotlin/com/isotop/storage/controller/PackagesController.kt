package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListPackagesDataResponse
import com.isotop.storage.service.PackageService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/packages")
open class PackagesController(
    private val packageService: PackageService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{storageId}", produces = ["application/json"])
    open fun getPackagesByStorageId(
        @PathVariable(value = "storageId")
        storageId: Int
    ): ListPackagesDataResponse {
        return packageService.getPackagesByStorageCode(storageId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{packageId}")
    open fun removePackage(
        @PathVariable(value = "packageId")
        packageId: Int
    ) {
        return packageService.removePackageById(packageId)
    }
}
