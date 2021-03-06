package com.isotop.storage.service.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.MakeTypeRequest
import com.isotop.storage.dto.response.ListMakeTypeDataResponse
import com.isotop.storage.dto.response.MakeTypeResponse
import com.isotop.storage.repository.directory.MakeTypesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class MakeTypeService(
    private val makeTypesRepository: MakeTypesRepository
) {
    open fun getMakeTypes(): ListMakeTypeDataResponse {
        return ListMakeTypeDataResponse(makeTypesRepository.getMakeTypes())
    }

    open fun getMakeTypeById(makeId: Int): MakeTypeResponse {
        if (!makeTypesRepository.isExistMakeTypeById(makeId)) {
            throw ValidationException(10)
        }
        return makeTypesRepository.getMakeTypeById(makeId)[0]
    }

    @Transactional
    open fun addMakeType(makeTypeRequest: MakeTypeRequest): MakeTypeResponse {
        validatAddMakeTypeRequest(makeTypeRequest)
        return makeTypesRepository.addMakeType(makeTypeRequest)
    }

    @Transactional
    open fun updateMakeType(makeTypeRequest: MakeTypeRequest): MakeTypeResponse {
        validateUpdateMakeTypeRequest(makeTypeRequest)
        return makeTypesRepository.updateMakeType(makeTypeRequest)
    }

    @Transactional
    open fun removeMakeType(id: Int) {
        if (!makeTypesRepository.isExistMakeTypeById(id)) {
            throw ValidationException(18)
        }
        return makeTypesRepository.removeMakeType(id)
    }

    private fun validatAddMakeTypeRequest(makeTypeRequest: MakeTypeRequest) {
        if (makeTypeRequest.makeTypeName.isNullOrBlank()
            || makeTypesRepository.isExistMakeTypeByName(makeTypeRequest.makeTypeName)
        ) {
            throw ValidationException(15)
        }
    }

    private fun validateUpdateMakeTypeRequest(makeTypeRequest: MakeTypeRequest) {
        if (!makeTypesRepository.isExistMakeTypeById(makeTypeRequest.makeTypeCode)) {
            throw ValidationException(16)
        }
        if (makeTypeRequest.makeTypeName.isNullOrBlank()
        ) {
            throw ValidationException(17)
        }
    }
}