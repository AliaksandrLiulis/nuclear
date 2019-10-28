package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListNuclideTypeDataResponse
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.service.NuclideTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nuclidetypes")
open class NuclideTypeController(
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
}