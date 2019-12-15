package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListEventDataResponse
import com.isotop.storage.repository.MoutionRepository
import org.springframework.stereotype.Service

@Service
open class EventService(
    private val moutionRepository: MoutionRepository
) {

    open fun getAllActiveEvent(): ListEventDataResponse {
        return ListEventDataResponse(moutionRepository.getActiveMotionEvent())
    }

    open fun deactivateEvent(motionCodeId: Int) {
        if (!moutionRepository.isExistMotionByMotionCode(motionCodeId)) {
            throw ValidationException(34)
        }
        moutionRepository.deactivateEvent(motionCodeId)
    }
}
