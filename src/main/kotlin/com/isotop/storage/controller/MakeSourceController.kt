package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListMakeSourceDataResponse
import com.isotop.storage.dto.response.ListStorageDataResponse
import com.isotop.storage.service.MakeSourceService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/makesources")
open class MakeSourceController(
    private val makeSourceService: MakeSourceService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllMakeSource(): ListMakeSourceDataResponse {
        return makeSourceService.getMakeSources()
    }

//    @PreAuthorize("hasRole('STORAGE')")
//    @PostMapping
//    open fun addMakeType(
//        @RequestBody
//        payload: MakeTypeRequest
//    ): MakeTypeResponse {
//        return makeTypeService.addMakeType(payload)
//    }
}