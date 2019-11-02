package com.isotop.storage.service

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.MakeRequest
import com.isotop.storage.dto.response.ListMakeTypeDataResponse
import com.isotop.storage.dto.response.MakeTypeResponse
import com.isotop.storage.repository.MakeRepository
import org.springframework.stereotype.Service

@Service
open class MakeService(
    private val makeRepository: MakeRepository
) {
    open fun getMakeTypes(): ListMakeTypeDataResponse {
        return ListMakeTypeDataResponse(makeRepository.getMakeTypes())
    }

    open fun getMakeTypeById(makeId: Int): MakeTypeResponse {
        if (!makeRepository.isExistMakeTypeById(makeId)) {
            throw ValidationException(10)
        }
        return makeRepository.getMakeTypeById(makeId)[0]
    }

    open fun addMakeType(makeRequest: MakeRequest): MakeTypeResponse {
        validatAddeMakeTypeRequest(makeRequest)
        return makeRepository.addMakeType(makeRequest)
    }

    open fun updateMakeType(makeRequest: MakeRequest): MakeTypeResponse {
        validateUpdateMakeTypeRequest(makeRequest)
        return makeRepository.updateMakeType(makeRequest)
    }

    open fun removeMakeType(id: Int) {
        if (!makeRepository.isExistMakeTypeById(id)) {
            throw ValidationException(18)
        }
        return makeRepository.removeMakeType(id)
    }

    private fun validatAddeMakeTypeRequest(makeRequest: MakeRequest) {
        if (makeRequest.makeTypeName.isNullOrBlank()
            || makeRepository.isExistMakeTypeByName(makeRequest.makeTypeName)
        ) {
            throw ValidationException(15)
        }
    }

    private fun validateUpdateMakeTypeRequest(makeRequest: MakeRequest) {
        if (!makeRepository.isExistMakeTypeById(makeRequest.nmakeTypeCode)) {
            throw ValidationException(16)
        }
        if (makeRequest.makeTypeName.isNullOrBlank()
        ) {
            throw ValidationException(17)
        }
    }
}