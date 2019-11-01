package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListNuclideTypeDataResponse
import com.isotop.storage.dto.response.NuclideTypeResponse
import com.isotop.storage.service.NuclideService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nuclides")
open class NuclideController(
    private val nuclideService: NuclideService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllNuclideTypes(): ListNuclideTypeDataResponse {
        return nuclideService.getNuclideTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{typeId}", produces = ["application/json"])
    open fun getNuclideTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): NuclideTypeResponse {
        return nuclideService.getNuclideTypeById(typeId)
    }
}
