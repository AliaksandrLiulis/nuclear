package com.isotop.storage.controller

import com.isotop.storage.dto.request.ActRequest
import com.isotop.storage.dto.response.ActResponse
import com.isotop.storage.dto.response.ListActsDataResponse
import com.isotop.storage.service.ActService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/acts")
open class ActsController(
    private val actService: ActService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllActs(): ListActsDataResponse {
        return actService.getActs()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("/{actId}", produces = ["application/json"])
    open fun getActById(
        @PathVariable(value = "actId")
        actId: Int
    ): ActResponse {
        return actService.getActById(actId)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addAct(
        @RequestBody
        payload: ActRequest
    ): ActResponse {
        return actService.addAct(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateAct(
        @RequestBody
        payload: ActRequest
    ): ActResponse {
        return actService.updateAct(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteActById(
        @PathVariable(value = "id")
        id: Int
    ) {
        return actService.removeActById(id)
    }
}
