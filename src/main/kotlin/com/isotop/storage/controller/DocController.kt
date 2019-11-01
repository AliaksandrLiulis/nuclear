package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListDocTypeDataResponse
import com.isotop.storage.service.DocService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/docs")
open class DocController(
    private val docService: DocService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllDocTypes(): ListDocTypeDataResponse {
        return docService.getDocTypes()
    }
}