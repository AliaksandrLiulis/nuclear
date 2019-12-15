package com.isotop.storage.service.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.UpdateMotionRequest
import com.isotop.storage.dto.response.ListMoutionDataResponse
import com.isotop.storage.dto.response.ListMoutionTypeDataResponse
import com.isotop.storage.dto.response.MoutionResponse
import com.isotop.storage.repository.MoutionRepository
import org.springframework.stereotype.Service

@Service
open class MoutionService(
    private val moutionRepository: MoutionRepository
) {
    open fun getMoutionTypes(): ListMoutionTypeDataResponse {
        return ListMoutionTypeDataResponse(moutionRepository.getMoutionTypes())
    }

    open fun getAllMotions(): ListMoutionDataResponse {
        return ListMoutionDataResponse(moutionRepository.getMoutions())
    }

    open fun updateMotionByCode(payload: UpdateMotionRequest): MoutionResponse {
        if (!moutionRepository.isExistMotionByMotionCode(payload.moutionCode)) {
            throw ValidationException(34)
        }
        return moutionRepository.updateMoutionByMotionCode(payload)
    }
}
