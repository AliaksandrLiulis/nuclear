package com.isotop.storage.service.types

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListSourceTypeDataResponse
import com.isotop.storage.dto.response.SourceTypeResponse
import com.isotop.storage.repository.types.SourceTypeRepository
import org.springframework.stereotype.Service

@Service
open class SourceTypeService(
    private val sourceTypeRepository: SourceTypeRepository
) {
    open fun getSourceTypes(): ListSourceTypeDataResponse {
        return ListSourceTypeDataResponse(sourceTypeRepository.getSourceTypes())
    }

    open fun getSourceTypeById(sourceTypeId: Int): SourceTypeResponse {
        if (!sourceTypeRepository.isExistSourceType(sourceTypeId)) {
            throw ValidationException(10)
        }
        return sourceTypeRepository.getSourceTypeById(sourceTypeId)[0]
    }
}
