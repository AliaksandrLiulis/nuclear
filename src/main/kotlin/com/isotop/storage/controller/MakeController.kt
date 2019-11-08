package com.isotop.storage.controller

import com.isotop.storage.dto.request.MakeTypeRequest
import com.isotop.storage.dto.response.ListMakeTypeDataResponse
import com.isotop.storage.dto.response.MakeTypeResponse
import com.isotop.storage.service.directory.MakeTypeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/makes")
open class MakeController(
    private val makeTypeService: MakeTypeService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllMakeTypes(): ListMakeTypeDataResponse {
        return makeTypeService.getMakeTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{typeId}", produces = ["application/json"])
    open fun getMakeTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): MakeTypeResponse {
        return makeTypeService.getMakeTypeById(typeId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addMakeType(
        @RequestBody
        payload: MakeTypeRequest
    ): MakeTypeResponse {
        return makeTypeService.addMakeType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateMakeType(
        @RequestBody
        payload: MakeTypeRequest
    ): MakeTypeResponse {
        return makeTypeService.updateMakeType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteMakeType(
        @PathVariable(value = "id")
        id: Int
    ) {
        return makeTypeService.removeMakeType(id)
    }
}
