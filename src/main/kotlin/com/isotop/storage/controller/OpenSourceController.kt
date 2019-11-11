package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListOpenSourceTypeDataResponse
import com.isotop.storage.dto.response.OpenSourceTypeResponse
import com.isotop.storage.service.directory.OpenSourceTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/opensource")
open class OpenSourceController(
    private val openSourceTypeService: OpenSourceTypeService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/types",produces = ["application/json"])
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
}
