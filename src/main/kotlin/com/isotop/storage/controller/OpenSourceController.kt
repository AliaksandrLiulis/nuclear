package com.isotop.storage.controller

import com.isotop.storage.dto.request.OpenSourceUsingRequest
import com.isotop.storage.dto.response.ListOpenSourceTypeDataResponse
import com.isotop.storage.dto.response.OpenSourceTypeResponse
import com.isotop.storage.service.OpenSourceService
import com.isotop.storage.service.directory.OpenSourceTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/opensources")
open class OpenSourceController(
    private val openSourceTypeService: OpenSourceTypeService,
    private val openSourceService: OpenSourceService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/types", produces = ["application/json"])
    open fun getAllOpenSourceTypes(): ListOpenSourceTypeDataResponse {
        return openSourceTypeService.getOpenSourceTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/types/{typeId}", produces = ["application/json"])
    open fun getOpenSourceTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): OpenSourceTypeResponse {
        return openSourceTypeService.getOpenSourceTypeById(typeId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping("/using", produces = ["application/json"])
    open fun updateOpenSourceUsing(
        @RequestBody
        payload: OpenSourceUsingRequest
    ) {
        openSourceService.updateOpenSourceUsing(payload)
    }
}
