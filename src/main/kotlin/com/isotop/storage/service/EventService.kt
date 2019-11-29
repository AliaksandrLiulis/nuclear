package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListEventDataResponse
import com.isotop.storage.repository.MoutionRepository
import org.springframework.stereotype.Service

@Service
open class EventService(
    private val motionRepository: MoutionRepository
) {

    open fun getAllActiveEvent(): ListEventDataResponse {
        return ListEventDataResponse(motionRepository.getActiveMotionEvent())
    }

    open fun deactivateEvent(motionCodeId: Int) {
        if (motionRepository.getMoutionById(motionCodeId).isEmpty()) {
            throw ValidationException(33)
        }
        motionRepository.deactivateEvent(motionCodeId)
    }
}