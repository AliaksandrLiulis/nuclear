package com.isotop.storage.service

import com.isotop.storage.dto.response.ListMoutionTypeDataResponse
import com.isotop.storage.repository.MoutionRepository
import org.springframework.stereotype.Service

@Service
open class MoutionService(
    private val moutionRepository: MoutionRepository
) {
    open fun getMoutionTypes(): ListMoutionTypeDataResponse {
        return ListMoutionTypeDataResponse(moutionRepository.getMoutionTypes())
    }
}
