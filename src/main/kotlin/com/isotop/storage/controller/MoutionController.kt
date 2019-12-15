package com.isotop.storage.controller

import com.isotop.storage.dto.request.UpdateMotionRequest
import com.isotop.storage.dto.response.ListMoutionDataResponse
import com.isotop.storage.dto.response.ListMoutionTypeDataResponse
import com.isotop.storage.dto.response.MoutionDto
import com.isotop.storage.service.directory.MoutionService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/moutions")
open class MoutionController(
    private val moutionService: MoutionService
) {

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping("types", produces = ["application/json"])
    open fun getAllMoutionTypes(): ListMoutionTypeDataResponse {
        return moutionService.getMoutionTypes()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @GetMapping( produces = ["application/json"])
    open fun getMoutions(): ListMoutionDataResponse {
        return moutionService.getAllMotions()
    }

    @PreAuthorize("hasRole('STORAGE')")
    @PutMapping( produces = ["application/json"])
    open fun updateMoutionByMoutionCode(
        @RequestBody
        payload: UpdateMotionRequest
    ): MoutionDto {
        return moutionService.updateMotionByCode(payload)
    }
}
