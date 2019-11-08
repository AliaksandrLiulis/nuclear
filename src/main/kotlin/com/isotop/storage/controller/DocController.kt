package com.isotop.storage.controller

import com.isotop.storage.dto.request.DocTypeRequest
import com.isotop.storage.dto.response.DocTypeResponse
import com.isotop.storage.dto.response.ListDocTypeDataResponse
import com.isotop.storage.service.directory.DocTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/docs")
open class DocController(
    private val docTypeService: DocTypeService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllDocTypes(): ListDocTypeDataResponse {
        return docTypeService.getDocTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{typeId}", produces = ["application/json"])
    open fun getDocTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): DocTypeResponse {
        return docTypeService.getDocTypeById(typeId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addDocType(
        @RequestBody
        payload: DocTypeRequest
    ): DocTypeResponse {
        return docTypeService.addDocType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateDocType(
        @RequestBody
        payload: DocTypeRequest
    ): DocTypeResponse {
        return docTypeService.updateDocType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteDocType(
        @PathVariable(value = "id")
        id: Int
    ) {
        return docTypeService.removeDocType(id)
    }
}
