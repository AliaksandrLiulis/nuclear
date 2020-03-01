package com.isotop.storage.controller

import com.isotop.storage.dto.request.MakeSourceRequest
import com.isotop.storage.dto.response.ListMakeSourceDataResponse
import com.isotop.storage.dto.response.MakeSourceResponse
import com.isotop.storage.service.MakeSourceService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/makesources")
open class MakeSourceController(
    private val makeSourceService: MakeSourceService
) {

    @PreAuthorize("hasRole('STORAGE') or hasRole('WORKER')")
    @GetMapping(produces = ["application/json"])
    open fun getMakeSources(): ListMakeSourceDataResponse {
        return makeSourceService.getMakeSources()
    }

    @PreAuthorize("hasRole('STORAGE') or hasRole('WORKER')")
    @PostMapping(produces = ["application/json"])
    open fun addMakeSource(
        @RequestBody
        payload: MakeSourceRequest
    ): MakeSourceResponse {
        return makeSourceService.addMakeSource(payload)
    }

    @PreAuthorize("hasRole('STORAGE') or hasRole('WORKER')")
    @PutMapping(produces = ["application/json"])
    open fun updateMakeSource(
        @RequestBody
        payload: MakeSourceRequest
    ): MakeSourceResponse {
        return makeSourceService.updateMakeSource(payload)
    }

    @PreAuthorize("hasRole('STORAGE') or hasRole('WORKER')")
    @DeleteMapping("/{storageId}")
    open fun deleteMakeSourceByStorageId(
        @PathVariable(value = "storageId")
        storageId: Int
    ) {
        return makeSourceService.removeMakeSourceByStorageId(storageId)
    }
}
