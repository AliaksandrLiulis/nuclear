package com.isotop.storage.service

import com.isotop.storage.dto.response.ListEventDataResponse
import com.isotop.storage.repository.MoutionRepository
import org.springframework.stereotype.Service

@Service
open class EventService(
    private val motionRepository: MoutionRepository
) {

    open fun getAllActiveAvent(): ListEventDataResponse {
        return ListEventDataResponse(motionRepository.getActiveMotionEvent())
    }
}