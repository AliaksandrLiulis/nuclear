package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListSourceTypeDataResponse
import com.isotop.storage.dto.response.SourceTypeResponse
import com.isotop.storage.service.types.SourceTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sources")
open class SourceController(
    private val sourceTypeService: SourceTypeService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllSourceTypes(): ListSourceTypeDataResponse {
        return sourceTypeService.getSourceTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{typeId}", produces = ["application/json"])
    open fun getSourceTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): SourceTypeResponse {
        return sourceTypeService.getSourceTypeById(typeId)
    }
}
