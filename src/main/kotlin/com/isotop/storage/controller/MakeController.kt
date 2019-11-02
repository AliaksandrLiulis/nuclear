package com.isotop.storage.controller

import com.isotop.storage.dto.request.MakeRequest
import com.isotop.storage.dto.response.ListMakeTypeDataResponse
import com.isotop.storage.dto.response.MakeTypeResponse
import com.isotop.storage.service.MakeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/makes")
open class MakeController(
    private val makeService: MakeService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllMakeTypes(): ListMakeTypeDataResponse {
        return makeService.getMakeTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{typeId}", produces = ["application/json"])
    open fun getMakeTypeById(
        @PathVariable(value = "typeId")
        typeId: Int
    ): MakeTypeResponse {
        return makeService.getMakeTypeById(typeId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addMakeType(
        @RequestBody
        payload: MakeRequest
    ): MakeTypeResponse {
        return makeService.addMakeType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateMakeType(
        @RequestBody
        payload: MakeRequest
    ): MakeTypeResponse {
        return makeService.updateMakeType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteMakeType(
        @PathVariable(value = "id")
        id: Int
    ) {
        return makeService.removeMakeType(id)
    }
}