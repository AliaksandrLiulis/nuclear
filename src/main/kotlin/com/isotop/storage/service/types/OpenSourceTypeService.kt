package com.isotop.storage.service.types

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.response.ListOpenSourceTypeDataResponse
import com.isotop.storage.dto.response.OpenSourceTypeResponse
import com.isotop.storage.repository.types.OpenSourceTypeRepository
import org.springframework.stereotype.Service

@Service
open class OpenSourceTypeService(
    private val openSourceTypeRepository: OpenSourceTypeRepository
) {
    open fun getOpenSourceTypes(): ListOpenSourceTypeDataResponse {
        return ListOpenSourceTypeDataResponse(openSourceTypeRepository.getOpenSourceTypes())
    }

    open fun getOpenSourceTypeById(sourceTypeId: Int): OpenSourceTypeResponse {
        if (!openSourceTypeRepository.isExistOpenSourceType(sourceTypeId)) {
            throw ValidationException(10)
        }
        return openSourceTypeRepository.getOpenSourceTypeById(sourceTypeId)[0]
    }
}
