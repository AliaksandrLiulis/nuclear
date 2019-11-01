package com.isotop.storage.service

import com.isotop.storage.dto.response.ListMakeTypeDataResponse
import com.isotop.storage.repository.MakeRepository
import org.springframework.stereotype.Service

@Service
open class MakeService(
    private val makeService: MakeRepository
) {
    open fun getMakeTypes(): ListMakeTypeDataResponse {
        return ListMakeTypeDataResponse(makeService.getMakeTypes())
    }
}