package com.isotop.storage.controller

import com.isotop.storage.dto.request.ActFormatAddRequest
import com.isotop.storage.dto.request.ActFormatUpdateRequest
import com.isotop.storage.dto.response.ActFormatResponse
import com.isotop.storage.dto.response.ListActsFormatDataResponse
import com.isotop.storage.service.directory.ActFormatService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/act_formats")
open class ActFormatController(
    private val actFormatService: ActFormatService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getActFormats(): ListActsFormatDataResponse {
        return actFormatService.getActFormats()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PostMapping(produces = ["application/json"])
    open fun addActFormat(
        @RequestBody
        payload: ActFormatAddRequest
    ): ActFormatResponse {
        return actFormatService.addActFormat(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping(produces = ["application/json"])
    open fun updateActFormat(
        @RequestBody
        payload: ActFormatUpdateRequest
    ): ActFormatResponse {
        return actFormatService.updateDocType(payload)
    }

    @PreAuthorize("hasRole('STORAGE')")
    @DeleteMapping("/{actId}")
    open fun deleteActFormat(
        @PathVariable(value = "actId")
        actId: Int
    ) {
        return actFormatService.removeDocType(actId)
    }
}
