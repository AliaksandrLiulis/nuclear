package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListMoutionTypeDataResponse
import com.isotop.storage.service.MoutionService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/moutions")
open class MoutionController(
    private val moutionService: MoutionService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllMoutionTypes(): ListMoutionTypeDataResponse {
        return moutionService.getMoutionTypes()
    }
}
