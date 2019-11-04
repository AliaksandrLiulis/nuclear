package com.isotop.storage.controller

import com.isotop.storage.dto.request.NuclideRequest
import com.isotop.storage.dto.response.ListNuclideTypeDataResponse
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.service.types.NuclideTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nuclides")
open class NuclideController(
    private val nuclideTypeService: NuclideTypeService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllNuclideTypes(): ListNuclideTypeDataResponse {
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
    @PostMapping
    open fun addNuclide(
        @RequestBody
        payload: NuclideRequest
    ): NuclideTypeResponse {
        return nuclideTypeService.addNuclide(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateNuclide(
        @RequestBody
        payload: NuclideRequest
    ): NuclideTypeResponse {
        return nuclideTypeService.updateNuclide(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteNuclide(
        @PathVariable(value = "id")
        id: Int
    ) {
        return nuclideTypeService.removeNuclide(id)
    }
}
