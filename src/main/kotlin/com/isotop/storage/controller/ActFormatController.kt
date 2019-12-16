package com.isotop.storage.controller

import com.isotop.storage.dto.request.ActFormatAddRequest
import com.isotop.storage.dto.request.ActFormatUpdateRequest
import com.isotop.storage.dto.response.ActFormatResponse
import com.isotop.storage.dto.response.ListActsFormatDataResponse
import com.isotop.storage.service.directory.ActFormatService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/acts_formats")
open class ActFormatController(
    private val actFormatService: ActFormatService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllActsFormat(): ListActsFormatDataResponse {
        return actFormatService.getActFormats()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping
    open fun addActFormat(
        @RequestBody
        payload: ActFormatAddRequest
    ): ActFormatResponse {
        return actFormatService.addActFormat(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping
    open fun updateActFormat(
        @RequestBody
        payload: ActFormatUpdateRequest
    ): ActFormatResponse {
        return actFormatService.updateDocType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{id}")
    open fun deleteActFormat(
        @PathVariable(value = "id")
        id: Int
    ) {
        return actFormatService.removeDocType(id)
    }
}
