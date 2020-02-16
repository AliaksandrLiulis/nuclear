package com.isotop.storage.controller

import com.isotop.storage.dto.request.VarMainRequest
import com.isotop.storage.dto.response.ListVarMainResponse
import com.isotop.storage.dto.response.VarMainResponse
import com.isotop.storage.service.directory.VarMainService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/varmain")
open class VarMainController(
    private val varMainService: VarMainService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllVarMains(): ListVarMainResponse {
        return varMainService.getVarMain()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{varMainId}", produces = ["application/json"])
    open fun getVarMainById(
        @PathVariable(value = "varMainId")
        actId: Int
    ): VarMainResponse {
        return varMainService.getVarMainById(actId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addVarMain(
        @RequestBody
        payload: VarMainRequest
    ): VarMainResponse {
        return varMainService.addVarMain(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateVarMain(
        @RequestBody
        payload: VarMainRequest
    ): VarMainResponse {
        return varMainService.updateVarMain(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteVarMainById(
        @PathVariable(value = "id")
        id: Int
    ) {
        return varMainService.removeVarMain(id)
    }
}
