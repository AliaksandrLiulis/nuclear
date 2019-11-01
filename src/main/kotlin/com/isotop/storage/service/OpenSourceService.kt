package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListOpenSourceTypeDataResponse
import com.isotop.storage.dto.response.OpenSourceTypeResponse
import com.isotop.storage.repository.OpenSourceRepository
import org.springframework.stereotype.Service

@Service
open class OpenSourceService(
    private val openSourceRepository: OpenSourceRepository
) {
    open fun getOpenSourceTypes(): ListOpenSourceTypeDataResponse {
        return ListOpenSourceTypeDataResponse(openSourceRepository.getOpenSourceTypes())
    }

    open fun getOpenSourceTypeById(sourceTypeId: Int): OpenSourceTypeResponse {
        if (!openSourceRepository.isExistOpenSourceType(sourceTypeId)) {
            throw ValidationException(10)
        }
        return openSourceRepository.getOpenSourceTypeById(sourceTypeId)[0]
    }
}
