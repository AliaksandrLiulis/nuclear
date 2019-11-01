package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListMakeTypeDataResponse
import com.isotop.storage.service.MakeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}