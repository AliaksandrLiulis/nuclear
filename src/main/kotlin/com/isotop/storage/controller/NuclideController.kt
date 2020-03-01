package com.isotop.storage.controller

import com.isotop.storage.dto.request.NuclideRequest
import com.isotop.storage.dto.response.ListNuclideTypeDataResponse
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.service.directory.NuclideTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nuclides")
open class NuclideController(
    private val nuclideTypeService: NuclideTypeService
) {

    @PreAuthorize("hasRole('STORAGE') or hasRole('WORKER')")
    @GetMapping(produces = ["application/json"])
    open fun getNuclideTypes(): ListNuclideTypeDataResponse {
        return nuclideTypeService.getNuclideTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{typeId}", produces = ["application/json"])
    open fun getNuclideTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): NuclideTypeResponse {
        return nuclideTypeService.getNuclideTypeById(typeId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addNuclide(
        @RequestBody
        payload: NuclideRequest
    ): NuclideTypeResponse {
        return nuclideTypeService.addNuclide(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping(produces = ["application/json"])
    open fun updateNuclide(
        @RequestBody
        payload: NuclideRequest
    ): NuclideTypeResponse {
        return nuclideTypeService.updateNuclide(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{typeId}")
    open fun deleteNuclide(
        @PathVariable(value = "typeId")
        typeId: Int
    ) {
        return nuclideTypeService.removeNuclide(typeId)
    }
}
