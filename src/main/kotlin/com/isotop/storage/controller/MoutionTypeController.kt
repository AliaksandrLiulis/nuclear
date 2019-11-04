package com.isotop.storage.controller

import com.isotop.storage.dto.response.ListMoutionTypeDataResponse
import com.isotop.storage.service.types.MoutionTypesService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/moution_types")
open class MoutionTypeController(
    private val moutionTypeTypesService: MoutionTypesService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping(produces = ["application/json"])
    open fun getAllMoutionTypes(): ListMoutionTypeDataResponse {
        return moutionTypeTypesService.getMoutionTypes()
    }
}
