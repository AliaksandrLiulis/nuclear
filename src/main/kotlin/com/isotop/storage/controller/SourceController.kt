package com.isotop.storage.controller

import com.isotop.storage.dto.AddSourceRequest
import com.isotop.storage.dto.response.ListSourceTypeDataResponse
import com.isotop.storage.dto.response.SourceTypeResponse
import com.isotop.storage.dto.response.StorageResponse
import com.isotop.storage.service.SourceService
import com.isotop.storage.service.directory.SourceTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sources")
open class SourceController(
    private val sourceService: SourceService,
    private val sourceTypeService: SourceTypeService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/types", produces = ["application/json"])
    open fun getAllSourceTypes(): ListSourceTypeDataResponse {
        return sourceTypeService.getSourceTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/types/{typeId}", produces = ["application/json"])
    open fun getSourceTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): SourceTypeResponse {
        return sourceTypeService.getSourceTypeById(typeId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addSource(
        @RequestBody
        payload: AddSourceRequest
    ): StorageResponse {
        payload.moutionType = 1
        payload.sourceTypeCode = 2
        return sourceService.addSource(payload)
    }
}
