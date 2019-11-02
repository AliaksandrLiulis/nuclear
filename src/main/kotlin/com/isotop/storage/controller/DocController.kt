package com.isotop.storage.controller

import com.isotop.storage.dto.request.DocTypeRequest
import com.isotop.storage.dto.response.DocTypeResponse
import com.isotop.storage.dto.response.ListDocTypeDataResponse
import com.isotop.storage.service.DocService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

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

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{typeId}", produces = ["application/json"])
    open fun getDocTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): DocTypeResponse {
        return docService.getDocTypeById(typeId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addDocType(
        @RequestBody
        payload: DocTypeRequest
    ): DocTypeResponse {
        return docService.addDocType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateDocType(
        @RequestBody
        payload: DocTypeRequest
    ): DocTypeResponse {
        return docService.updateDocType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteDocType(
        @PathVariable(value = "id")
        id: Int
    ) {
        return docService.removeDocType(id)
    }
}