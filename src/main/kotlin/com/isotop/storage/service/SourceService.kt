package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListSourceTypeDataResponse
import com.isotop.storage.dto.response.SourceTypeResponse
import com.isotop.storage.repository.SourceRepository
import org.springframework.stereotype.Service

@Service
open class SourceService(
    private val sourceRepository: SourceRepository
) {
    open fun getSourceTypes(): ListSourceTypeDataResponse {
        return ListSourceTypeDataResponse(sourceRepository.getSourceTypes())
    }

    open fun getSourceTypeById(sourceTypeId: Int): SourceTypeResponse {
        if (!sourceRepository.isExistSourceType(sourceTypeId)) {
            throw ValidationException(10)
        }
        return sourceRepository.getSourceTypeById(sourceTypeId)[0]
    }
}
